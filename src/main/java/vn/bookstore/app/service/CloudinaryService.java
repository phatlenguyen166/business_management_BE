package vn.bookstore.app.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {

    String uploadFile(MultipartFile file) throws IOException;

    void deleteFile(String imageUrl) throws IOException;

    String extractPublicId(String imageUrl);
}
