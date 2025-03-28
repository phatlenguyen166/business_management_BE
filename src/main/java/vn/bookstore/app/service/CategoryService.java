package vn.bookstore.app.service;

import java.util.List;

import vn.bookstore.app.dto.request.ReqCategoryDTO;
import vn.bookstore.app.dto.response.ResCategoryDTO;

public interface CategoryService {

    ResCategoryDTO createCategory(ReqCategoryDTO reqCategoryDTO);

    ResCategoryDTO updateCategory(Long id, ReqCategoryDTO reqCategoryDTO);

    void disableCategory(Long id);

    ResCategoryDTO getCategoryById(Long id);

    List<ResCategoryDTO> getAllCategories();
}
