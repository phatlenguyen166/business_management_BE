package vn.bookstore.app.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.bookstore.app.dto.response.ResAllowanceDTO;
import vn.bookstore.app.mapper.AllowanceMapper;
import vn.bookstore.app.repository.AllowanceRepository;
import vn.bookstore.app.service.AllowanceService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AllowanceServiceImpl implements AllowanceService {
    private final AllowanceRepository allowanceRepository;
    private final AllowanceMapper allowanceMapper;

    @Override
    public List<ResAllowanceDTO> handleGetAll() {
        return this.allowanceRepository.findAll().stream()
                .map(allowanceMapper::convertToResAllowanceDTO)
                .collect(Collectors.toList());
    }
}
