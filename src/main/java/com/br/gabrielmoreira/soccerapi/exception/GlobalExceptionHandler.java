package com.br.gabrielmoreira.soccerapi.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFound(
            ResourceNotFoundException ex, HttpServletRequest request) {

        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiError> handleBusinessException(
            BusinessException ex, HttpServletRequest request) {

        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Business Rule Violation",
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    /**
     * Violações de constraint no banco: tratado como regra de negócio (400), não 500.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrity(
            DataIntegrityViolationException ex, HttpServletRequest request) {

        String message = DataIntegrityExceptionMapper.toUserMessage(ex);
        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Business Rule Violation",
                message,
                request.getRequestURI()
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleNotReadable(
            HttpMessageNotReadableException ex, HttpServletRequest request) {

        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                "Corpo da requisição inválido, JSON malformado ou tipo de dado incompatível.",
                request.getRequestURI()
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, MissingServletRequestParameterException.class})
    public ResponseEntity<ApiError> handleBadRequestParams(
            Exception ex, HttpServletRequest request) {

        String message = ex.getMessage() != null ? ex.getMessage() : "Parâmetro da requisição inválido.";
        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                message,
                request.getRequestURI()
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(
            Exception ex, HttpServletRequest request) {
        log.error("Erro inesperado em {}", request.getRequestURI(), ex);
        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "Erro inesperado no servidor. Tente novamente ou contate o suporte.",
                request.getRequestURI()
        );

        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
