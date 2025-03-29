package az.edu.turing.msauth.mapper.University;

import az.edu.turing.msauth.entity.University;
import az.edu.turing.msauth.model.request.UniversityRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UniversityMapper {

    UniversityMapper INSTANCE = Mappers.getMapper(UniversityMapper.class);

    University toEntity(UniversityRequest universityRequest);
}
