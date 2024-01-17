package com.alten.shop.back.products.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class EntityAlreadyExistException extends RuntimeException {
    private final String message;
}
