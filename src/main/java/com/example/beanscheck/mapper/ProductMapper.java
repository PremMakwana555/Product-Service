package com.example.beanscheck.mapper;

import com.example.beanscheck.dto.FakeStoreProductResponseDto;
import com.example.beanscheck.dto.ProductResponseDto;
import com.example.beanscheck.models.Category;
import com.example.beanscheck.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "image", target = "imageUrl")
    @Mapping(source = "category", target = "category")
    Product fakeStoreProductResponseDtoToProduct(FakeStoreProductResponseDto dto);

    @Mapping(source = "category.name", target = "category")
    FakeStoreProductResponseDto productToFakeStoreProductResponseDto(Product product);

    ProductResponseDto ProductDtoToProduct(Product product);

    Product ProductDtoToProduct(ProductResponseDto productResponseDto);

    @Mapping(source = "categoryName", target = "category")
    @Mapping(source = "imageUrl", target = "image")
    FakeStoreProductResponseDto ProductDtoToFakeStoreProductResponseDto(ProductResponseDto productResponseDto);

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
}
