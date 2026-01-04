package com.example.product_service.mapper;

import com.example.product_service.dto.ProductDto;
import com.example.product_service.models.Category;
import com.example.product_service.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDto productDtoToProduct(Product product);

    Product productDtoToProduct(ProductDto productDto);

    default Category map(String value) {
        if (value == null) {
            return null;
        }
        Category category = new Category();
        category.setName(value);
        // You might want to set an ID here if needed, or handle it separately
        return category;
    }

    default String map(Category category) {
        return category == null ? null : category.getName();
    }

    @Mapping(source = "categoryName", target = "name")
    @Mapping(source = "categoryDescription", target = "description")
    Category productDtoToCategory(ProductDto productDto);

    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "category.description", target = "categoryDescription")
    ProductDto productToProductDto(Product product);
}
