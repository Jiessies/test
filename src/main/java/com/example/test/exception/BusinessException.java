package com.example.test.exception;

/**
 * 业务异常
 *
 * @author xudong
 * @date 2021/3/19
 * @blame Platform RD Team
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String errorCode;

	public BusinessException(String errorCode) {
		this(errorCode, "");
	}
	
	public BusinessException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

    public String getErrorCode() {
        return this.errorCode;
    }

}
