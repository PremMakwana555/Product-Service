package com.example.beanscheck.mapper;

import com.example.beanscheck.dto.FakeStoreProductResponseDto;
import com.example.beanscheck.dto.ProductResponseDto;
import com.example.beanscheck.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "image", target = "imageUrl")
    Product productToFakeStoreProductResponseDto(FakeStoreProductResponseDto dto);

    ProductResponseDto ProductDtoToProduct(Product product);
}
