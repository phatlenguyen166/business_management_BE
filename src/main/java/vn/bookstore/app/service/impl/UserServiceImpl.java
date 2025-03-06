package vn.bookstore.app.service.impl;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.bookstore.app.dto.request.ReqUserDTO;
import vn.bookstore.app.dto.request.ReqUserWithContractDTO;
import vn.bookstore.app.dto.response.ResContractDTO;
import vn.bookstore.app.dto.response.ResUserDTO;
import vn.bookstore.app.mapper.ContractMapper;
import vn.bookstore.app.mapper.UserConverter;
import vn.bookstore.app.model.Contract;
import vn.bookstore.app.model.User;
import vn.bookstore.app.repository.UserRepository;
import vn.bookstore.app.service.UserService;
import vn.bookstore.app.util.error.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
//@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
//    private final UserRepository userRepository;
//    private final UserConverter userConverter;
//    private final PasswordEncoder passwordEncoder;
//    private final ContractServiceImpl contractService;

    private  UserRepository userRepository;
    private  UserConverter userConverter;
    private  PasswordEncoder passwordEncoder;
    private  ContractServiceImpl contractService;
    private ContractMapper contractMapper;
    public UserServiceImpl (UserRepository userRepository,
                            UserConverter userConverter,
                            @Lazy
                            PasswordEncoder passwordEncoder,
                            @Lazy
                            ContractServiceImpl contractService,
                            ContractMapper contractMapper){
        this.contractService =contractService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userConverter =userConverter;
        this.contractMapper = contractMapper;
    }

    @Override
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsernameAndStatus(username, 1).orElseThrow(() -> new UsernameNotFoundException(
                "Username not found or user is not active"));
    }

    public List<ResUserDTO> handleFetchAllUser() {
        List<ResUserDTO> resUserDTOS = new ArrayList<>();
        for (User user : userRepository.findAllByStatus(1)) {
            ResContractDTO resContractDTO = contractMapper.convertToResContractDTO(contractService.getActiveContract(user.getContracts()));
            ResUserDTO resUserDTO = this.userConverter.convertToResUserDTO(user, resContractDTO);
            resUserDTOS.add(resUserDTO);
        }
        return resUserDTOS;

    }


    public ResUserDTO handleCreateUser(ReqUserWithContractDTO reqUser) {
        String hashPassWord = this.passwordEncoder.encode(reqUser.getPassword());
        reqUser.setPassword(hashPassWord);
        User newUser = this.userConverter.convertToUser(reqUser);
        newUser.setStatus(1);
        this.userRepository.save(newUser);
        try {
            Contract contract = this.contractService.handleCreateContractWithUser(reqUser, newUser.getId());
            ResContractDTO resContractDTO = contractMapper.convertToResContractDTO(contract);
            return this.userConverter.convertToResUserDTO(newUser, resContractDTO);
        } catch (RuntimeException e) {
            this.userRepository.delete(newUser);
            throw new RuntimeException("Lỗi khi tạo hợp đồng: "+ e.getMessage());
        }

    }

    public ResUserDTO handleFetchUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            ResContractDTO contractDTO = contractMapper.convertToResContractDTO(contractService.getActiveContract(user.get().getContracts()));
            return this.userConverter.convertToResUserDTO(user.get(), contractDTO);
        }
        return null;
    }

    public ResUserDTO handleUpdateUser(ReqUserDTO updateUser, Long id) {
        Optional<User> currentUser = this.userRepository.findById(id);
        String hashPassWord;
        if (updateUser.getPassword() == "" || updateUser.getPassword() == null) {
            hashPassWord = this.passwordEncoder.encode(currentUser.get().getPassword());
        } else {
            hashPassWord = this.passwordEncoder.encode(updateUser.getPassword());
        }

        if (currentUser.isPresent()) {
            currentUser.get().setFullName(updateUser.getFullName());
            currentUser.get().setAddress(updateUser.getAddress());
            currentUser.get().setDateOfBirth(updateUser.getDateOfBirth());
            currentUser.get().setEmail(updateUser.getEmail());
            currentUser.get().setGender(updateUser.getGender());
            currentUser.get().setPhoneNumber(updateUser.getPhoneNumber());
            currentUser.get().setPassword(hashPassWord);
            this.userRepository.save(currentUser.get());
            ResContractDTO contractDTO = contractMapper.convertToResContractDTO(contractService.getActiveContract(currentUser.get().getContracts()));
            return this.userConverter.convertToResUserDTO(currentUser.get(), contractDTO);
        }
        return null;
    }

    public void handleDeleteUser(Long id) {
        Optional<User> currentUser = this.userRepository.findById(id);
        if (currentUser.isPresent()) {
            currentUser.get().setStatus(0);
            this.userRepository.save(currentUser.get());
        }
    }

    public boolean isExistUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean isActive(Long id) {
        Optional<User> currentUser = this.userRepository.findById(id);
        if (currentUser.isPresent()) {
            if (currentUser.get().getStatus() == 0) {
                return false;
            }
            return true;
        }
        return false;
    }




}
