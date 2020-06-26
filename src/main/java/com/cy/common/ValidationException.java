package com.cy.common;

/**
 * @author wh
 * @date 2020/5/27
 * Description: 自定义异常
 */
public class ValidationException extends RuntimeException {
    private static final long serialVersionUID = -724013866886900528L;

    public ValidationException() {
        super();
    }
    public ValidationException(String message) {
        super(message);
    }
    public ValidationException(Throwable cause) {
        super(cause);
    }

}
