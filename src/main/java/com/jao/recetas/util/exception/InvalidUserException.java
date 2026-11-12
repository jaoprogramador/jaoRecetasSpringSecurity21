package com.jao.recetas.util.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InvalidUserException extends RuntimeException {
    public InvalidUserException(String message) {
        super(message);
        log.error(message);
    }
}
