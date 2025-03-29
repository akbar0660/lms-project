package az.edu.turing.msauth.mapper.student;

import az.edu.turing.msauth.entity.Student;
import az.edu.turing.msauth.model.request.StudentRequest;
import az.edu.turing.msauth.model.response.StudentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    StudentResponse toResponse(Student student);

    Student toEntity(StudentRequest studentRequest);
}
