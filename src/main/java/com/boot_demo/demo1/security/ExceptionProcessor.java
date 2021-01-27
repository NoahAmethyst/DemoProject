package com.boot_demo.demo1.security;

import com.boot_demo.demo1.model.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
public class ExceptionProcessor {

    private static final String REQUEST_ID_UNRECOGNIZED = "UNRECOGNIZED";

    private static final String SOURCE_UNDEFINED = "UNDEFINED";


    // 通用异常捕捉
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ResponseModel> processException(Exception ex, WebRequest request) {
        String requestId = extractRequestId(request);
        log.error("requestId: " + requestId + " " + ex.getLocalizedMessage(), ex);
        return new ResponseEntity<>(ResponseModel.buildComplete(HttpStatus.INTERNAL_SERVER_ERROR.value(), "System error:" + ex.getLocalizedMessage()), HttpStatus.OK);
    }

    private String extractRequestId(WebRequest request) {
        String requestId = request.getParameter("requestId");
        if (requestId == null || requestId.trim().length() == 0) {
            requestId = REQUEST_ID_UNRECOGNIZED;
        } else if (requestId.length() > 100) {
            requestId = requestId.substring(0, 100) + "...";
        }
        return requestId;
    }

    // 参数校验异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ResponseModel> processMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        String msg = "";
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            msg += fieldError.getDefaultMessage() + "!";
        }
        return new ResponseEntity<>(ResponseModel.buildComplete(HttpStatus.BAD_REQUEST.value(), msg), HttpStatus.OK);
    }

    @ExceptionHandler(BindException.class)
    public final ResponseEntity<ResponseModel> processBindException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        String msg = "";
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            msg += fieldError.getDefaultMessage() + "!";
        }
        return new ResponseEntity<>(ResponseModel.buildComplete(HttpStatus.BAD_REQUEST.value(), msg), HttpStatus.OK);
    }

}