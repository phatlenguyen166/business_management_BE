package vn.bookstore.app.mapper;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import vn.bookstore.app.dto.request.ReqUserDTO;
import vn.bookstore.app.dto.response.ResUserDTO;
import vn.bookstore.app.model.User;

@Component
public class UserConverter {
    private ModelMapper modelMapper;

    public UserConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public User convertToUser(ReqUserDTO requestDTO) {
        User result = modelMapper.map(requestDTO, User.class);
        return result;
    }

    public ResUserDTO convertToResUserDTO(User user) {
        ResUserDTO result = modelMapper.map(user, ResUserDTO.class);
        return result;
    }


}
