package com.homestay.common.exception;

import com.homestay.common.response.ResultCode;
import lombok.Getter;

@Getter
public class OrderException extends BusinessException {
    
    private static final long serialVersionUID = 1L;

    public OrderException(ResultCode resultCode) {
        super(resultCode);
    }

    public OrderException(ResultCode resultCode, String message) {
        super(resultCode, message);
    }

    public OrderException(String message) {
        super(message);
    }

    public OrderException(Integer code, String message) {
        super(code, message);
    }

    public OrderException(ResultCode resultCode, Object data) {
        super(resultCode, data);
    }
} 