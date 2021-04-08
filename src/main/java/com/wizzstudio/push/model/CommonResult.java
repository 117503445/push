package com.wizzstudio.push.model;


import lombok.Data;
import org.springframework.http.ResponseEntity;
@Data
public final class CommonResult<T> {

        private int code = 0;

        private String message = "";

        private T data;

        public CommonResult() {
        }

        public CommonResult(int code,String message, T data) {
           this.code = code;
           this.message=message;
           this.data=data;
        }


    }


