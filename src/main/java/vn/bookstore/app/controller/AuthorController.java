package vn.bookstore.app.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
import vn.bookstore.app.dto.AuthorRequestDTO;
import vn.bookstore.app.dto.AuthorResponseDTO;
import vn.bookstore.app.dto.response.ResponseDTO;
import vn.bookstore.app.service.AuthorService;

@RestController
@RequestMapping("/api/v1/author")
@RequiredArgsConstructor
@Tag(name = "Authors", description = "API quản lý tác giả")
public class AuthorController {

    private final AuthorService authorService;

    @Operation(summary = "Danh sách tác giả", description = "API lấy danh sách tất cả tác giả")
    @GetMapping("/list")
    public ResponseEntity<ResponseDTO<List<AuthorResponseDTO>>> getAllAuthors() {
        List<AuthorResponseDTO> authors = authorService.getAllAuthors();
        return ResponseEntity.ok(ResponseDTO.success(true, "Lấy danh sách tác giả thành công", authors));
    }

    @Operation(summary = "Lấy thông tin tác giả", description = "API lấy chi tiết một tác giả theo ID")
    @GetMapping("/{authorId}")
    public ResponseEntity<ResponseDTO<AuthorResponseDTO>> getAuthorById(@PathVariable Long authorId) {
        AuthorResponseDTO author = authorService.getAuthorById(authorId);
        return ResponseEntity.ok(ResponseDTO.success(true, "Lấy thông tin tác giả thành công", author));
    }

    @Operation(summary = "Tạo tác giả", description = "API này tạo một tác giả mới")
    @PostMapping("/add")
    public ResponseEntity<ResponseDTO<AuthorResponseDTO>> createAuthor(@Valid @RequestBody AuthorRequestDTO authorRequestDTO) {
        AuthorResponseDTO createdAuthor = authorService.createAuthor(authorRequestDTO);
        return ResponseEntity.ok(ResponseDTO.success(true, "Tạo tác giả thành công", createdAuthor));
    }

    @Operation(summary = "Cập nhật tác giả", description = "API này cập nhật thông tin một tác giả theo ID")
    @PutMapping("/{authorId}")
    public ResponseEntity<ResponseDTO<AuthorResponseDTO>> updateAuthor(
            @PathVariable Long authorId,
            @Valid @RequestBody AuthorRequestDTO authorRequestDTO) {
        AuthorResponseDTO updatedAuthor = authorService.updateAuthor(authorId, authorRequestDTO);
        return ResponseEntity.ok(ResponseDTO.success(true, "Cập nhật tác giả thành công", updatedAuthor));
    }

    // @Operation(summary = "Vô hiệu hóa tác giả", description = "API này vô hiệu hóa một tác giả theo ID")
    // @PatchMapping("/{authorId}")
    // public ResponseEntity<ResponseDTO<Void>> disableAuthor(@PathVariable Long authorId) {
    //     authorService.deleteAuthor(authorId); // Note: You may need to modify the service method to disable instead of delete
    //     return ResponseEntity.ok(ResponseDTO.success(true, "Vô hiệu hóa tác giả thành công", null));
    // }
}
