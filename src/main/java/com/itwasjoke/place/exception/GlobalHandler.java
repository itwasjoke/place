package com.itwasjoke.place.exception;

import com.itwasjoke.place.exception.couple.CoupleAccessDeniedException;
import com.itwasjoke.place.exception.couple.CoupleNotFoundException;
import com.itwasjoke.place.exception.user.UserAlreadyExistsException;
import com.itwasjoke.place.exception.user.UserNotFoundException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
            UserAlreadyExistsException.class,
            UserNotFoundException.class,
            CoupleNotFoundException.class,
            CoupleAccessDeniedException.class,
            ValidationException.class
    })
    public ResponseEntity<Object> handleAllException(Exception e, WebRequest request) {
        String responseBody;
        HttpStatus status;

        if (e instanceof UserAlreadyExistsException) {
            responseBody = "Такой пользователь уже существует";
            status = HttpStatus.BAD_REQUEST;
        } else if (e instanceof UserNotFoundException) {
            responseBody = "Пользователь не найден";
            status = HttpStatus.NOT_FOUND;
        } else if (e instanceof CoupleAccessDeniedException){
            responseBody = "Нет доступа к этой паре";
            status = HttpStatus.FORBIDDEN;
        } else if (e instanceof CoupleNotFoundException){
            responseBody = "Задача не найдена";
            status = HttpStatus.NOT_FOUND;
        } else if (e instanceof ValidationException) {
            responseBody = "Ошибка валидации: " + e.getMessage();
            status = HttpStatus.BAD_REQUEST;
        } else {
            responseBody = "Неизвестная ошибка";
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return handleExceptionInternal(e, responseBody, new HttpHeaders(), status, request);
    }
}