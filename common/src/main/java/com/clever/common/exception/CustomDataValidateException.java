package com.clever.common.exception;

public class CustomDataValidateException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = 1741078348564045051L;
    private String message;

    public CustomDataValidateException() {
        this.message = "";
    }

    public CustomDataValidateException(String message
    )

    {
        this.message = message;
    }

    public CustomDataValidateException(String message, Throwable t)

    {
        super(message, t);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
