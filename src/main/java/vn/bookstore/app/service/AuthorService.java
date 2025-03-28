package vn.bookstore.app.service;

import java.util.List;

import vn.bookstore.app.dto.AuthorRequestDTO;
import vn.bookstore.app.dto.AuthorResponseDTO;

public interface AuthorService {

    List<AuthorResponseDTO> getAllAuthors();

    AuthorResponseDTO getAuthorById(Long id);

    AuthorResponseDTO createAuthor(AuthorRequestDTO authorRequestDTO);

    AuthorResponseDTO updateAuthor(Long id, AuthorRequestDTO authorRequestDTO);

    void deleteAuthor(Long id);
}
