package br.com.pedroloureco.prodsjava.domain.mapper;

import br.com.pedroloureco.prodsjava.domain.user.User;
import br.com.pedroloureco.prodsjava.domain.user.UserInputDTO;
import br.com.pedroloureco.prodsjava.domain.user.UserOutputDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserInputDTO dto);
    UserOutputDTO toDTO(User user);
}
