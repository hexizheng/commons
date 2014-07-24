package com.jscn.commons.core.exceptions;

/**
 * 系统异常(使用者无法自行处理,需要开发人员干预的问题)
 * @description
 * @author hexizheng
 */
public class SystemException extends RuntimeException {

    /**
	 * 
	 */
    private static final long serialVersionUID = 5809164630995639478L;

    private String[]          args;

    /** 错误码 */
    protected String          errorCode;

    /**
	 * 
	 */
    public SystemException() {
        super();

    }

    /**
     * @param message
     * @param cause
     */
    public SystemException(String message, Throwable cause, String... args) {
        super(message, cause);
        this.args = args;

    }

    /**
     * @param message
     */
    public SystemException(String message, String... args) {
        super(message);
        this.args = args;
    }

    /**
     * 
     * @param errorCode
     * @param message
     */
    public SystemException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * @param message
     * @param cause
     */
    public SystemException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    /**
     * @param cause
     */
    public SystemException(Throwable cause) {
        super(cause);

    }

    public String[] getArgs() {
        return args;
    }

}
