package com.boot_demo.demo1.model;


import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ResponseModel {

    private Integer status;

    private String message;

    private Object data;

    public static ResponseModel buildSuccess(Object data) {
        return ResponseModel.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    public static ResponseModel buildComplete(int statusCode, String message) {
        return ResponseModel.builder()
                .status(statusCode)
                .message(message)
                .data(null)
                .build();
    }
}


