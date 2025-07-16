package com.shopdev.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

public class ResponseData<T> extends ResponseEntity<ResponseData.Payload<T>> implements Serializable {
    public ResponseData(HttpStatus statusCode, String message, T data) {
        super(new Payload<T>(statusCode.value(), message, data), statusCode);
    }

    public ResponseData(HttpStatus statusCode, String message) {
        super(new Payload<>(statusCode.value(), message), statusCode);
    }

    @Getter
    @Setter
    public static class Payload<T> {
        private int statusCode;
        private String message;
        private T data;

        public Payload(int statusCode, String message, T data) {
            this.statusCode = statusCode;
            this.message = message;
            this.data = data;
        }

        public Payload(int statusCode, String message) {
            this.statusCode = statusCode;
            this.message = message;
        }
    }
}
