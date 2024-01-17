package com.alten.shop.back.products.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
public class ObjectNotValidException extends RuntimeException {
    private final Set<String> errorMessages ;
}
