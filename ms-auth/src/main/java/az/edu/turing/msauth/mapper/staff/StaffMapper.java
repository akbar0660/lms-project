package az.edu.turing.msauth.mapper.staff;

import az.edu.turing.msauth.entity.Staff;
import az.edu.turing.msauth.model.request.StaffRequest;
import az.edu.turing.msauth.model.response.StaffResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StaffMapper {

    StaffMapper INSTANCE = Mappers.getMapper(StaffMapper.class);

    StaffResponse entityToResponse(Staff staff);

    Staff requestToEntity(StaffRequest StaffRequest);
}
