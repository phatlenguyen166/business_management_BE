package vn.bookstore.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import vn.bookstore.app.dto.request.ReqCategoryDTO;
import vn.bookstore.app.dto.response.ResCategoryDTO;
import vn.bookstore.app.model.Category;

/**
 * Mapper interface for converting between Category entities and DTOs using
 * MapStruct
 */
@Mapper(componentModel = "spring")
public interface CategoryMapper {

    /**
     * Converts a request DTO to a Category entity
     *
     * @param reqCategoryDTO the category request DTO
     * @return the Category entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "1") // Set default status as active
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    Category convertToCategory(ReqCategoryDTO reqCategoryDTO);

    /**
     * Converts a Category entity to a response DTO
     *
     * @param category the Category entity
     * @return the category response DTO
     */
    @Mapping(target = "productCount", expression = "java(category.getProducts() != null ? category.getProducts().size() : 0)")
    ResCategoryDTO convertToResCategoryDTO(Category category);
}
