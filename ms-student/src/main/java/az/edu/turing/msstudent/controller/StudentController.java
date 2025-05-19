package az.edu.turing.msstudent.controller;

import az.edu.turing.msstudent.model.*;
import az.edu.turing.msstudent.service.StudentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentServiceImpl studentService;

    // 1. Telebe profilne bax
    @GetMapping("/profile/{studentId}")
    @PreAuthorize("hasAnyRole('STUDENT', 'STAFF', 'SUPER_ADMIN')")
    public ResponseEntity<StudentDto> getStudentProfile(@PathVariable Long studentId) {
        StudentDto profile = studentService.getStudentProfile(studentId);
        return ResponseEntity.ok(profile);
    }

    // 2. Telebe profili yenile
    @PutMapping("/profile/{studentId}")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<StudentDto> updateProfile(
            @PathVariable Long studentId,
            @RequestBody UpdateProfileRequest request) {
        StudentDto updated = studentService.updateProfile(studentId, request);
        return ResponseEntity.ok(updated);
    }

    // 3. CV yukle
    @PostMapping("/profile/{studentId}/cv")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<FileResponseDto> uploadCV(
            @PathVariable Long studentId,
            @RequestParam("file") MultipartFile file) {
        FileResponseDto response = studentService.uploadCV(studentId, file);
        return ResponseEntity.ok(response);
    }

    // 4. Sekil yukle
    @PostMapping("/profile/{studentId}/pp")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<FileResponseDto> uploadProfilePhoto(
            @PathVariable Long studentId,
            @RequestParam("file") MultipartFile file) {
        FileResponseDto response = studentService.uploadProfilePhoto(studentId, file);
        return ResponseEntity.ok(response);
    }

    // 5.Exam neticelerine baxmaq
    @GetMapping("/profile/{studentId}/exam-results")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<List<ExamResultDto>> getExamResults(
            @PathVariable Long studentId,
            @RequestParam(value = "examType", required = false) ExamType examType,
            @RequestParam(value = "sortBy", defaultValue = "studentScore") String sortBy,
            @RequestParam(value = "direction", defaultValue = "DESC") String direction) {
        List<ExamResultDto> results = studentService.getStudentExamResults(studentId, examType, sortBy, direction);
        return ResponseEntity.ok(results);
    }

    // 6.Exam neticelrini elave elemek
    @PostMapping("/profile/{studentId}/exam-results")
    @PreAuthorize("hasAnyRole('STAFF', 'SUPER_ADMIN')")
    public ResponseEntity<ExamResultDto> addExamResult(
            @PathVariable Long studentId,
            @RequestBody ExamResultDto examResultDto) {
        ExamResultDto saved = studentService.addExamResult(studentId, examResultDto);
        return ResponseEntity.ok(saved);
    }

    // 7.Davamiyyet
    @PostMapping("/profile/attendance")
    @PreAuthorize("@customAuthorization.isClassRep()")
    public ResponseEntity<Void> markAttendance(
            @RequestBody AttendanceRequest request
    ) {
        studentService.markAttendance(request);
        return ResponseEntity.ok().build();
    }

    // 8.VideoRecord
    @PostMapping("/profile/{studentId}/lesson-video")
    @PreAuthorize("@customAuthorization.isClassRep()")
    public ResponseEntity<Void> uploadLessonVideo(
            @PathVariable Long studentId,
            @ModelAttribute LessonVideoRequest request
    ) {
        studentService.uploadLessonVideo(studentId, request);
        return ResponseEntity.ok().build();
    }

    // Telebeni sinifkom elemek
    @PostMapping("/profile/{studentId}/make-rep")
    @PreAuthorize("hasAnyRole('STAFF','SUPER_ADMIN')")
    public ResponseEntity<StudentDto> makeStudentRep(@PathVariable Long studentId) {
        StudentDto profile = studentService.makeRep(studentId);
        return ResponseEntity.ok(profile);
    }

    // Telebeni sinifkom elemek
    @PostMapping("/profile/{studentId}/make-mentor")
    @PreAuthorize("hasAnyRole('STAFF','SUPER_ADMIN')")
    public ResponseEntity<StudentDto> makeStudentMentor(@PathVariable Long studentId) {
        StudentDto profile = studentService.makeMentor(studentId);
        return ResponseEntity.ok(profile);
    }



   /* @PostMapping("/profile/{studentId}/grade-exam")
    @PreAuthorize("@customAuthorization.isMentor()")
    public ResponseEntity<ExamResultDto> gradeExam(
            @PathVariable Long studentId,
            @RequestBody GradeExamRequest request) {
        ExamResultDto result = studentService.gradeExam(studentId, request);
        return ResponseEntity.ok(result);
    }
*/

}
