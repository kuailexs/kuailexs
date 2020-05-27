package com.kuailexs.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义异常
 * @author gl
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class KuailexsException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    private String code;

    public KuailexsException(String code, String message) {
        super(message);
        this.code = code;
    }

    public KuailexsException(String code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public KuailexsException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public KuailexsException(String code, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

}
