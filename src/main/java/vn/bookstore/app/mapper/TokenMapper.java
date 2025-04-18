package vn.bookstore.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.bookstore.app.dto.response.ResTokenDTO;
import vn.bookstore.app.model.Role;
import vn.bookstore.app.model.User;

@Mapper(componentModel = "spring")
public interface TokenMapper {

    TokenMapper INSTANCE = Mappers.getMapper(TokenMapper.class);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "accessToken", target = "accessToken")
    @Mapping(source = "refreshToken", target = "refreshToken")
    @Mapping(source = "role.name", target = "roleName")
    ResTokenDTO mapToResTokenDTO(User user, String accessToken, String refreshToken, Role role);
}
