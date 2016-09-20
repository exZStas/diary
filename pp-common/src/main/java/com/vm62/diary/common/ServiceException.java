package com.vm62.diary.common;

/**
 *
 * Application exception thrown by Core or Service layer classes
 * and indicates recoverable problem which could be meaningful to user,
 * e.g. incorrect user input data
 * Thrown by core modules to services and by services to backend clients.
 *
 * ServiceException always rollback a transaction.
 *
 * @author
 *
 */

public class ServiceException extends Exception {

    /**
     * Error code corresponding to {@link ErrorType#getCode()}
     */
    private String errorTypeCode;

    protected ServiceException() {
        super();
    }

    /**
     * Service exception constructor.
     * As soon as ServiceException must be created by {@link ExceptionFactory} only,
     * constructor has package-visibility
     *
     * @param message
     * @param code error code corresponding to {@link ErrorType#getCode()}
     */
    protected ServiceException(String message, String code) {
        super(message);
        this.errorTypeCode = code;
    }

    /**
     * Service exception constructor.
     * As soon as ServiceException must be created by {@link ExceptionFactory} only,
     * constructor has package-visibility
     *
     * @param message
     * @param cause
     * @param code error code corresponding to {@link ErrorType#getCode()}
     */
    protected ServiceException(String message, Throwable cause, String code) {
        super(message, cause);
        this.errorTypeCode = code;
    }

    /**
     * @return error code corresponding to {@link ErrorType#getCode()}
     */
    public String getErrorTypeCode() {
        return errorTypeCode;
    }

    /**
     * Override default toString() to add error type code for correct exceptions logging.
     */
    @Override
    public String toString() {
        String message = "VC-" + errorTypeCode + ": " + getLocalizedMessage();
        return getClass().getName() + ": " + message;
    }

}
