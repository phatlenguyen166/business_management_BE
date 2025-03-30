package vn.bookstore.app.service;

import vn.bookstore.app.dto.response.ResAllowanceDTO;

import java.util.List;

public interface AllowanceService {
    public List<ResAllowanceDTO> handleGetAll();
}
