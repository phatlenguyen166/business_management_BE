package vn.bookstore.app.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import vn.bookstore.app.dto.request.ReqCategoryDTO;
import vn.bookstore.app.dto.response.ResCategoryDTO;
import vn.bookstore.app.dto.response.ResponseDTO;
import vn.bookstore.app.service.CategoryService;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
@Tag(name = "Categories", description = "API quản lý danh mục")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Danh sách danh mục", description = "API lấy danh sách tất cả danh mục")
    @GetMapping("/list")
    public ResponseEntity<ResponseDTO<List<ResCategoryDTO>>> getAllCategories() {
        List<ResCategoryDTO> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(ResponseDTO.success(true, "Lấy danh sách danh mục thành công", categories));
    }

    @Operation(summary = "Lấy thông tin danh mục", description = "API lấy chi tiết một danh mục theo ID")
    @GetMapping("/{categoryId}")
    public ResponseEntity<ResponseDTO<ResCategoryDTO>> getCategoryById(@PathVariable Long categoryId) {
        ResCategoryDTO category = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(ResponseDTO.success(true, "Lấy thông tin danh mục thành công", category));
    }

    @Operation(summary = "Tạo danh mục", description = "API này tạo một danh mục mới")
    @PostMapping("/add")
    public ResponseEntity<ResponseDTO<ResCategoryDTO>> createCategory(@RequestBody @Valid ReqCategoryDTO request) {
        ResCategoryDTO createdCategory = categoryService.createCategory(request);
        return ResponseEntity.ok(ResponseDTO.success(true, "Tạo danh mục thành công", createdCategory));
    }

    @Operation(summary = "Cập nhật danh mục", description = "API này cập nhật thông tin một danh mục theo ID")
    @PutMapping("/{categoryId}")
    public ResponseEntity<ResponseDTO<ResCategoryDTO>> updateCategory(
            @PathVariable Long categoryId,
            @RequestBody @Valid ReqCategoryDTO request) {
        ResCategoryDTO updatedCategory = categoryService.updateCategory(categoryId, request);
        return ResponseEntity.ok(ResponseDTO.success(true, "Cập nhật danh mục thành công", updatedCategory));
    }

    @Operation(summary = "Vô hiệu hóa danh mục", description = "API này vô hiệu hóa một danh mục theo ID")
    @PatchMapping("/{categoryId}")
    public ResponseEntity<ResponseDTO<Void>> disableCategory(@PathVariable Long categoryId) {
        categoryService.disableCategory(categoryId);
        return ResponseEntity.ok(ResponseDTO.success(true, "Vô hiệu hóa danh mục thành công", null));
    }
}
