package vn.bookstore.app.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.bookstore.app.dto.request.ReqCategoryDTO;
import vn.bookstore.app.dto.response.ResCategoryDTO;
import vn.bookstore.app.mapper.CategoryMapper;
import vn.bookstore.app.model.Category;
import vn.bookstore.app.repository.CategoryRepository;
import vn.bookstore.app.service.CategoryService;
import vn.bookstore.app.util.error.InvalidDataException;
import vn.bookstore.app.util.error.InvalidRequestException;
import vn.bookstore.app.util.error.NotFoundException;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public ResCategoryDTO createCategory(ReqCategoryDTO reqCategoryDTO) {
        // Kiểm tra tên danh mục đã tồn tại chưa
        if (categoryRepository.findByName(reqCategoryDTO.getName()).isPresent()) {
            throw new InvalidDataException("Danh mục với tên " + reqCategoryDTO.getName() + " đã tồn tại");
        }

        Category category = categoryMapper.convertToCategory(reqCategoryDTO);// Đặt trạng thái mặc định là active
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.convertToResCategoryDTO(savedCategory);
    }

    @Override
    public ResCategoryDTO updateCategory(Long id, ReqCategoryDTO reqCategoryDTO) {
        // Kiểm tra danh mục tồn tại, không lọc theo status
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy danh mục với ID: " + id));

        // Kiểm tra tên mới có trùng với danh mục khác không
        if (categoryRepository.existsByNameAndIdNot(reqCategoryDTO.getName(), id)) {
            throw new InvalidRequestException("Danh mục với tên " + reqCategoryDTO.getName() + " đã tồn tại");
        }

        category.setName(reqCategoryDTO.getName());
        Category updatedCategory = categoryRepository.save(category);
        return categoryMapper.convertToResCategoryDTO(updatedCategory);
    }

    @Override
    public void disableCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy danh mục với ID: " + id));

        if (category.getProducts() != null && !category.getProducts().isEmpty()) {
            throw new InvalidRequestException("Không thể vô hiệu hóa danh mục này vì có sản phẩm đang sử dụng");
        }

        // Xóa mềm bằng cách đặt status = 0
        category.setStatus(0);
        categoryRepository.save(category);
    }

    @Override
    public ResCategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy danh mục với ID: " + id));
        return categoryMapper.convertToResCategoryDTO(category);
    }

    @Override
    public List<ResCategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::convertToResCategoryDTO)
                .collect(Collectors.toList());
    }
}
