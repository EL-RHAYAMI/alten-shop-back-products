package com.alten.shop.back.products.mappers.response;

import com.alten.shop.back.products.dots.responses.ProductResDto;
import com.alten.shop.back.products.entities.Product;
import org.mapstruct.Mapper;

@Mapper
public interface ProductResMapper {
    Product toEntity(ProductResDto dto);
    ProductResDto toDto(Product entity);
}
