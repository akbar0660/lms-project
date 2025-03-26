package az.edu.turing.msauth.mapper.superadmin;

import az.edu.turing.msauth.entity.SuperAdmin;
import az.edu.turing.msauth.model.request.SuperAdminRequest;
import az.edu.turing.msauth.model.response.SuperAdminResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SuperAdminMapper {

    SuperAdminMapper INSTANCE = Mappers.getMapper(SuperAdminMapper.class);

    SuperAdminResponse superAdminToSuperAdminResponse(SuperAdmin SuperAdmin);

    SuperAdmin superAdminRequestToSuperAdmin(SuperAdminRequest SuperAdminRequest);
}
