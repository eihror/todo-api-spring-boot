package br.com.eihror.todo_list.core.exception;

import br.com.eihror.todo_list.core.model.ApiError;
import br.com.eihror.todo_list.core.model.ErrorCodes;
import br.com.eihror.todo_list.core.model.FieldError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        List<FieldError> fieldErrorList = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new FieldError(
                        fieldError.getField(),
                        fieldError.getDefaultMessage(),
                        ErrorCodes.resolve(fieldError)
                )).toList();

        return handleExceptionInternal(
                ex,
                new ApiError(fieldErrorList),
                headers,
                HttpStatus.BAD_REQUEST,
                request
        );
    }
}
