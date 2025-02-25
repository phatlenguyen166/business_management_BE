package vn.bookstore.app.mapper;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import vn.bookstore.app.model.User;

//@Component
//public class UserConverter {
//
//    @Autowired
//    private ModelMapper modelMapper;
//
//    public UserDTO convertToDto (UserEntity entity){
//        UserDTO result = modelMapper.map(entity, UserDTO.class);
//        return result;
//    }
//
//    public UserEntity convertToEntity (UserDTO dto){
//        UserEntity result = modelMapper.map(dto, UserEntity.class);
//        return result;
//    }
//
//}
@Component
public class UserConverter {
    private ModelMapper modelMapper;
    public UserConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

 }
