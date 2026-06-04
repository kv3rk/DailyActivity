package com.daily.plan.common.exception;

import com.daily.plan.DailyPlan.DTO.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateGoalException.class)
    public ResponseEntity<ApiErrorResponse> handleDuplicateGoal(
            DuplicateGoalException ex
    ) {
        return ResponseEntity.badRequest().body(
                new ApiErrorResponse(
                        ex.getMessage(),
                        null
                )
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleConstraintViolationException(
            ConstraintViolationException exception
    ) {
        List<String> errors = exception.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .toList();

        return new ResponseEntity<>(
                new ApiErrorResponse("Validation failed", errors),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception
    ) {
        List<String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        return new ResponseEntity<>(
                new ApiErrorResponse("Validation failed", errors),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(Exception.class)
    public Object handleGeneralException(
            Exception exception,
            HttpServletRequest request
    ) {
        String uri = request.getRequestURI();

        // API calls -> JSON
        if (uri.startsWith("/daily/save")
                || uri.startsWith("/daily/toggle")
                || uri.startsWith("/daily/active")
                || uri.startsWith("/daily/done")) {

            ApiErrorResponse error = new ApiErrorResponse(
                    "Request failed",
                    List.of(exception.getMessage())
            );

            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // page rendering -> redirect
        return "redirect:/daily/error";
    }
}