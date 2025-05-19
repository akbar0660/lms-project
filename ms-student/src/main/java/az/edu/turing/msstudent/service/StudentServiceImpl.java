package az.edu.turing.msstudent.service;

import az.edu.turing.msstudent.client.AuthServiceClient;
import az.edu.turing.msstudent.entity.AttendanceRecord;
import az.edu.turing.msstudent.entity.ExamResult;
import az.edu.turing.msstudent.entity.LessonVideoRecord;
import az.edu.turing.msstudent.entity.StudentProfile;
import az.edu.turing.msstudent.mapper.StudentMapper;
import az.edu.turing.msstudent.messaging.StudentEventProducer;
import az.edu.turing.msstudent.model.*;
import az.edu.turing.msstudent.repo.AttendanceRecordRepository;
import az.edu.turing.msstudent.repo.ExamResultRepository;
import az.edu.turing.msstudent.repo.LessonVideoRecordRepository;
import az.edu.turing.msstudent.repo.StudentRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final ExamResultRepository examResultRepository;
    private final AttendanceRecordRepository attendanceRecordRepository;
    private final FileStorageServiceImpl fileStorageServiceImpl;
    private final StudentEventProducer studentEventProducer;
    private final LessonVideoRecordRepository lessonVideoRecordRepository;
    private final AuthServiceClient authServiceClient;


    public void createProfileFromEvent(StudentCreatedEvent event) {
        StudentProfile profile = new StudentProfile();
        profile.setId(event.getStudentId());
        profile.setName(event.getName());
        profile.setUsername(event.getUsername());
        studentRepository.save(profile);
    }


    @Override
    public StudentDto getStudentProfile(Long studentId) {
        StudentProfile student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
        return StudentMapper.mapToProfileDto(student);
    }


    @Override
    public StudentDto updateProfile(Long studentId, UpdateProfileRequest request) {
        StudentProfile student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));

        student.setAboutMe(request.getAbout());
        student.setInstagramLink(request.getInstUrl());
        student.setLinkedinLink(request.getLinkedinUrl());

        StudentProfile updatedStudent = studentRepository.save(student);
        return StudentMapper.mapToProfileDto(updatedStudent);
    }

    @Override
    public FileResponseDto uploadCV(Long studentId, MultipartFile file) {
        // Validation
        if (file.isEmpty()) {
            throw new IllegalArgumentException("CV file cannot be empty");
        }

        /*if (!isValidFileType(file, "pdf", "docx")) {
            throw new IllegalArgumentException("Only PDF and DOCX files are allowed");
        }*/

        FileResponseDto fileResponse = fileStorageServiceImpl.storeFile(file, studentId);

        StudentProfile student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
        student.setCvUrl(fileResponse.getFileUrl());
        studentRepository.save(student);

        return fileResponse;
    }

    @Override
    public FileResponseDto uploadProfilePhoto(Long studentId, MultipartFile image) {
        if (image.isEmpty()) {
            throw new IllegalArgumentException("Profile photo file cannot be empty");
        }

        FileResponseDto fileResponse = fileStorageServiceImpl.storeFile(image, studentId);

        StudentProfile student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
        student.setProfilePictureUrl(fileResponse.getFileUrl());
        studentRepository.save(student);
        return fileResponse;
    }

    public List<ExamResultDto> getStudentExamResults(Long studentId, ExamType examType, String sortBy, String direction) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        StudentProfile user = studentRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (user.getId() != null && !user.getId().equals(studentId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only view your own exam results");
        }

        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        List<ExamResult> results;
        if (examType != null) {
            results = examResultRepository.findByStudentIdAndExamType(studentId, examType, sort);
        } else {
            results = examResultRepository.findByStudentId(studentId, sort);
        }
        return StudentMapper.mapList(results, StudentMapper::mapToDto);
    }

    public ExamResultDto addExamResult(Long studentId, ExamResultDto dto) {
        ExamResult result = ExamResult.builder()
                .studentId(studentId)
                .examType(dto.getExamType())
                .examName(dto.getExamName())
                .pdfUrl(dto.getPdfUrl())
                .maxScore(dto.getMaxScore())
                .studentScore(dto.getStudentScore())
                .examDate(dto.getExamDate())
                .build();
        ExamResult saved = examResultRepository.save(result);

        return StudentMapper.mapToDto(saved);
    }

    public void markAttendance(AttendanceRequest request) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        StudentProfile classRep = studentRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
        Long groupId = classRep.getGroupId();

        List<Long> groupStudentIds = studentRepository.findAllByGroupId(groupId)
                .stream().map(StudentProfile::getId).toList();

        for (Long id : request.getPresentStudentIds()) {
            if (!groupStudentIds.contains(id)) {
                throw new IllegalArgumentException("Invalid student id in attendance list: " + id);
            }
        }

        AttendanceRecord existing = attendanceRecordRepository.findByGroupIdAndLessonDate(request.getGroupId(), request.getLessonDate())
                .orElse(null);
        if (existing != null) {
            if (!existing.getPresentStudentIds().equals(request.getPresentStudentIds())) {
                AttendanceChangedMessage message = new AttendanceChangedMessage();
                message.setGroupId(request.getGroupId());
                message.setLessonDate(request.getLessonDate());
                message.setMessage("Attendance Changed");
                message.setName(classRep.getName());
                message.setUsername(classRep.getUsername());
                studentEventProducer.sendAttendanceChangedEvent(message);

            }
            existing.setPresentStudentIds(request.getPresentStudentIds());
            attendanceRecordRepository.save(existing);
        } else {
            AttendanceRecord record = new AttendanceRecord();
            record.setGroupId(request.getGroupId());
            record.setLessonDate(request.getLessonDate());
            record.setPresentStudentIds(request.getPresentStudentIds());
            attendanceRecordRepository.save(record);
        }
    }

    public void uploadLessonVideo (Long studentId,LessonVideoRequest request) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        StudentProfile classRep = studentRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));

        Long classRepGroupId = classRep.getGroupId();

        if (!classRepGroupId.equals(request.getGroupId())) {
            throw new IllegalArgumentException("Class rep can only upload videos for their own group!");
        }

        LessonVideoRecord existing = lessonVideoRecordRepository
                .findByGroupIdAndLessonDate(request.getGroupId(), request.getLessonDate())
                .orElse(null);

        FileResponseDto fileResponse = fileStorageServiceImpl.storeFile(request.getVideo(), studentId);
        String videoUrl = fileResponse.getFileUrl();


        if (existing != null) {
            existing.setVideoUrl(videoUrl);
            lessonVideoRecordRepository.save(existing);
        } else {
            LessonVideoRecord record = new LessonVideoRecord();
            record.setGroupId(request.getGroupId());
            record.setLessonDate(request.getLessonDate());
            record.setVideoUrl(videoUrl);
            lessonVideoRecordRepository.save(record);
        }
    }

    public StudentDto makeRep(Long studentId) {
        StudentProfile classRep = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
        classRep.setClassRep(true);
        return StudentMapper.mapToProfileDto(classRep);
    }

    public StudentDto makeMentor(Long studentId) {
        StudentProfile classMentor = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
        classMentor.setMentor(true);
        return StudentMapper.mapToProfileDto(classMentor);
    }

}
