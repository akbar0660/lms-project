package az.edu.turing.msstudent.mapper;

import az.edu.turing.msstudent.entity.ExamResult;
import az.edu.turing.msstudent.entity.StudentProfile;
import az.edu.turing.msstudent.model.ExamResultDto;
import az.edu.turing.msstudent.model.StudentDto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StudentMapper {
    public static <T, R> List<R> mapList(List<T> source, Function<T, R> mapper) {
        return source.stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    public static StudentDto mapToProfileDto(StudentProfile student) {
        return StudentDto.builder()
                .username(student.getUsername())
                .fullName(student.getName())
                .groupId(student.getGroupId())
                .aboutMe(student.getAboutMe())
                .profilePictureUrl(student.getProfilePictureUrl())
                .cvUrl(student.getCvUrl())
                .instagramLink(student.getInstagramLink())
                .linkedinLink(student.getLinkedinLink())
                .gpa(student.getGpa())
                .isClassRep(student.isClassRep())
                .isMentor(student.isMentor())
                .build();
    }

    public static ExamResultDto mapToDto(ExamResult examResult) {
        return ExamResultDto.builder()
                .examType(examResult.getExamType())
                .examName(examResult.getExamName())
                .pdfUrl(examResult.getPdfUrl())
                .maxScore(examResult.getMaxScore())
                .studentScore(examResult.getStudentScore())
                .examDate(examResult.getExamDate())
                .build();
    }

}
