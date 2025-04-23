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
import vn.bookstore.app.model.*;
import vn.bookstore.app.repository.*;
import vn.bookstore.app.service.UserService;
import vn.bookstore.app.util.error.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
// @RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    // private final UserRepository userRepository;
    // private final UserConverter userConverter;
    // private final PasswordEncoder passwordEncoder;
    // private final ContractServiceImpl contractService;

    private UserRepository userRepository;
    private UserConverter userConverter;
    private PasswordEncoder passwordEncoder;
    private ContractServiceImpl contractService;
    private ContractMapper contractMapper;
    private LeaveReqRepository leaveReqRepository;
    private ContractRepository contractRepository;
    private AttendanceRepository attendanceRepository;
    private AttendanceDetailRepository attendanceDetailRepository;

    public UserServiceImpl(UserRepository userRepository,
            UserConverter userConverter,
            @Lazy PasswordEncoder passwordEncoder,
            @Lazy ContractServiceImpl contractService,
            ContractMapper contractMapper,
            LeaveReqRepository leaveReqRepository,
            ContractRepository contractRepository,
            AttendanceRepository attendanceRepository,
            AttendanceDetailRepository attendanceDetailRepository) {
        this.contractService = contractService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userConverter = userConverter;
        this.contractMapper = contractMapper;
        this.leaveReqRepository = leaveReqRepository;
        this.contractRepository = contractRepository;
        this.attendanceRepository = attendanceRepository;
        this.attendanceDetailRepository = attendanceDetailRepository;
    }

    @Override
    public ResUserDTO handleFetchUserByUsername(String username) {
        User user = userRepository.findByUsernameAndStatus(username, 1)
                .orElseThrow(() -> new ResourceNotFoundException("Người dùng không tồn tại hoặc không hoạt động"));
        ResContractDTO contractDTO = contractMapper
                .convertToResContractDTO(contractService.getActiveContract(user.getContracts()));
        return userConverter.convertToResUserDTO(user, contractDTO);
    }

    @Override
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsernameAndStatus(username, 1)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Username not found or user is not active"));
    }

    public List<ResUserDTO> handleFetchAllUser() {
        List<ResUserDTO> resUserDTOS = new ArrayList<>();
        for (User user : userRepository.findAllByStatus(1)) {
            ResContractDTO resContractDTO = contractMapper
                    .convertToResContractDTO(contractService.getActiveContract(user.getContracts()));
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
            throw new RuntimeException("Lỗi khi tạo hợp đồng: " + e.getMessage());
        }

    }

    public ResUserDTO handleFetchUserById(Long id) {
        Optional<User> user = userRepository.findByIdAndStatus(id, 1);
        if (user.isPresent()) {
            ResContractDTO contractDTO = contractMapper
                    .convertToResContractDTO(contractService.getActiveContract(user.get().getContracts()));
            return this.userConverter.convertToResUserDTO(user.get(), contractDTO);
        }
        return null;
    }

    public ResUserDTO handleUpdateUser(ReqUserDTO updateUser, Long id) {
        Optional<User> currentUser = this.userRepository.findByIdAndStatus(id, 1);
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
            ResContractDTO contractDTO = contractMapper
                    .convertToResContractDTO(contractService.getActiveContract(currentUser.get().getContracts()));
            return this.userConverter.convertToResUserDTO(currentUser.get(), contractDTO);
        }
        return null;
    }

    public void handleDeleteUser(Long id) {
        Optional<User> currentUser = this.userRepository.findByIdAndStatus(id, 1);
        if (currentUser.isPresent()) {
            List<LeaveRequest> leaveRequests = this.leaveReqRepository.findByUserAndStatus(currentUser.get(), 1);
            for (LeaveRequest leaveRequest : leaveRequests) {
                leaveRequest.setStatus(0);
                this.leaveReqRepository.save(leaveRequest);
            }
            List<Contract> contracts = this.contractRepository.getAllByUserId(id);
            for (Contract contract : contracts) {
                contract.setStatus(0);
                this.contractRepository.save(contract);
            }
            User user1 = currentUser.get();
            List<Attendance> attendances = this.attendanceRepository.findAllByUserOrderByMonthOfYearDesc(user1);
            for (Attendance attendance : attendances) {
                List<AttendanceDetail> attendanceDetails = this.attendanceDetailRepository
                        .findAllByAttendance(attendance);
                this.attendanceDetailRepository.deleteAll(attendanceDetails);
            }
            this.attendanceRepository.deleteAll(attendances);
            currentUser.get().setStatus(0);
            this.userRepository.save(currentUser.get());
        }
    }

    public boolean isExistUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean isActive(Long id) {
        Optional<User> currentUser = this.userRepository.findByIdAndStatus(id, 1);
        if (currentUser.isPresent()) {
            if (currentUser.get().getStatus() == 0) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public Long saveUser(User user) {
        return userRepository.save(user).getId();
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Email không tồn tại!."));
    }

    @Override
    public User findByUsernameAndStatus(String username, int status) {
        return userRepository.findByUsernameAndStatus(username, 1)
                .orElseThrow(() -> new UsernameNotFoundException("Tên đăng nhập hoặc mật khẩu không chính xác"));
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Người dùng không tồn tại"));
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Người dùng không tồn tại"));
    }

}
