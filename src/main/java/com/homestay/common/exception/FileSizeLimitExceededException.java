package com.homestay.common.exception;

import lombok.Getter;

/**
 * 文件大小超出限制异常
 */
@Getter
public class FileSizeLimitExceededException extends RuntimeException {
    
    /**
     * 允许的最大文件大小（字节）
     */
    private final long permittedSize;
    
    /**
     * 实际文件大小（字节）
     */
    private final long actualSize;

    public FileSizeLimitExceededException(String message, long permittedSize, long actualSize) {
        super(message);
        this.permittedSize = permittedSize;
        this.actualSize = actualSize;
    }

    public FileSizeLimitExceededException(long permittedSize, long actualSize) {
        this(String.format("文件大小超出限制，最大允许%d字节，实际大小%d字节", permittedSize, actualSize), 
             permittedSize, actualSize);
    }
} 