package com.alten.shop.back.products.mappers.requests;

import com.alten.shop.back.products.dots.requests.ProductReqDto;
import com.alten.shop.back.products.entities.Product;
import org.mapstruct.Mapper;

@Mapper
public interface ProductReqMapper {
    Product toEntity(ProductReqDto dto);
    ProductReqDto toDto(Product entity);
}
