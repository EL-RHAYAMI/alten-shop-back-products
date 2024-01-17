package com.alten.shop.back.products.services.impl;

import com.alten.shop.back.products.dots.requests.ProductReqDto;
import com.alten.shop.back.products.dots.responses.ProductResDto;
import com.alten.shop.back.products.entities.Product;
import com.alten.shop.back.products.exceptions.EntityAlreadyExistException;
import com.alten.shop.back.products.exceptions.FieldNotFoundException;
import com.alten.shop.back.products.mappers.requests.ProductReqMapper;
import com.alten.shop.back.products.mappers.response.ProductResMapper;
import com.alten.shop.back.products.repositories.ProductRepository;
import com.alten.shop.back.products.services.ProductService;
import com.alten.shop.back.products.validators.ObjectValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductReqMapper productReqMapper;
    private final ProductResMapper productResMapper;
    private final ObjectValidator<ProductReqDto> productValidator;

    @Override
    public ProductResDto create(ProductReqDto dto) {
        productValidator.validate(dto);

        if (productRepository.findByCode(dto.getCode()).isPresent()) {
            throw new EntityAlreadyExistException("Product already exists");
        }

        return productResMapper.toDto(
                productRepository.save(
                        productReqMapper.toEntity(dto)
                )
        );
    }

    @Override
    public List<ProductResDto> retrieveAll() {

        return productRepository.findAll().stream()
                .map(productResMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResDto retrieveDetails(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        return productResMapper.toDto(product);
    }

    @Override
    public ProductResDto update(Long id, Map<String, Object> fields) {

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        //Update the existing product with the new values from the fields map using reflection
        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Product.class, key);
            if(field == null) throw new FieldNotFoundException("Field '" +key + "' not found");
            field.setAccessible(true);
            ReflectionUtils.setField(field, existingProduct, value);
        });


        //Validate the mapped ProductReqDto
        productValidator.validate(productReqMapper.toDto(existingProduct));


        //Save the updated product to the database and map it to a ProductResDto
        return productResMapper.toDto(
                productRepository.save(existingProduct)
        );
    }

    @Override
    public void remove(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        productRepository.delete(product);
    }


}
