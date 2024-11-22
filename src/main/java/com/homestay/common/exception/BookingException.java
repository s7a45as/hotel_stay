package com.homestay.common.exception;

import com.homestay.common.response.ResultCode;
import lombok.Getter;

@Getter
public class BookingException extends BusinessException {
    
    private static final long serialVersionUID = 1L;

    public BookingException(ResultCode resultCode) {
        super(resultCode);
    }

    public BookingException(ResultCode resultCode, String message) {
        super(resultCode, message);
    }

    public BookingException(String message) {
        super(message);
    }

    public BookingException(Integer code, String message) {
        super(code, message);
    }

    public BookingException(ResultCode resultCode, Object data) {
        super(resultCode, data);
    }
} 