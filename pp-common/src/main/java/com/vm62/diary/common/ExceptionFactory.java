package com.vm62.diary.common;

import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;

/**
 *
 * Factory class for creating code-based exceptions
 *
 * @see ServiceException
 * @see ErrorType
 *
 */
@Slf4j
public class ExceptionFactory {

    public enum ErrorLogLevel {
        DEBUG,
        INFO,
        WARN,
        ERROR
    }

    /**
     *
     * Creates and throws {@link ServiceException}.
     * Uses code and template string from {@link ErrorType} and in-place arguments to
     * construct exception message text.
     *
     * @param errorType type of error
     * @param inplaceArgs in-place arguments to construct message text
     * @throws ServiceException
     */
    public static void throwServiceException(ErrorType errorType, Object ... inplaceArgs) throws ServiceException {
        throwServiceException(errorType, ErrorLogLevel.ERROR, inplaceArgs);
    }

    /**
     *
     * Creates and throws {@link ServiceException}.
     * Uses code and template string from {@link ErrorType} and in-place arguments to
     * construct exception message text.
     *
     * @param errorType type of error
     * @param inplaceArgs in-place arguments to construct message text
     * @param logLevel define how to log exception
     * @throws ServiceException
     */
    public static void throwServiceException(ErrorType errorType, ErrorLogLevel logLevel, Object ... inplaceArgs) throws ServiceException {
        ServiceException serviceException = getServiceException(errorType, inplaceArgs);
        logError(logLevel, serviceException);
        throw serviceException;
    }

    /**
     *
     * Creates and throws {@link ServiceException}
     * with NO in-place arguments in message template
     *
     * @param errorType type of error
     * @throws ServiceException
     */
    public static void throwServiceException(ErrorType errorType) throws ServiceException {
        throwServiceException(errorType, ErrorLogLevel.ERROR);
    }

    /**
     *
     * Creates and throws {@link ServiceException}
     * with NO in-place arguments in message template
     *
     * @param errorType type of error
     * @param logLevel define how to log exception
     * @throws ServiceException
     */
    public static void throwServiceException(ErrorType errorType, ErrorLogLevel logLevel) throws ServiceException {
        ServiceException serviceException = getServiceException(errorType, (Object)null);
        logError(logLevel, serviceException);
        throw serviceException;
    }

    /**
     *
     * Creates and throws {@link ServiceException}.
     * Uses code and template string from {@link ErrorType} and in-place arguments to
     * construct exception message text.
     *
     * @param cause
     * @param errorType type of error
     * @param inplaceArgs in-place arguments to construct message text
     * @throws ServiceException
     */
    public static void throwServiceException(Throwable cause, ErrorType errorType, Object ... inplaceArgs) throws ServiceException {
        throwServiceException(cause, errorType, ErrorLogLevel.ERROR, inplaceArgs);
    }

    /**
     *
     * Creates and throws {@link ServiceException}.
     * Uses code and template string from {@link ErrorType} and in-place arguments to
     * construct exception message text.
     *
     * @param cause
     * @param errorType type of error
     * @param logLevel define how to log exception
     * @param inplaceArgs in-place arguments to construct message text
     * @throws ServiceException
     */
    public static void throwServiceException(Throwable cause, ErrorType errorType, ErrorLogLevel logLevel, Object ... inplaceArgs) throws ServiceException {
        ServiceException serviceException = getServiceException(cause, errorType, inplaceArgs);
        logError(logLevel, serviceException);
        throw serviceException;
    }

    /**
     *
     * Creates and throws {@link ServiceException}
     * with NO in-place arguments in message template
     *
     * @param cause
     * @param errorType type of error
     * @throws ServiceException
     */
    public static void throwServiceException(Throwable cause, ErrorType errorType) throws ServiceException {
        throwServiceException(cause, errorType, ErrorLogLevel.ERROR);
    }


    /**
     *
     * Creates and throws {@link ServiceException}
     * with NO in-place arguments in message template
     *
     * @param cause
     * @param errorType type of error
     * @param logLevel define how to log exception
     * @throws ServiceException
     */
    public static void throwServiceException(Throwable cause, ErrorType errorType, ErrorLogLevel logLevel) throws ServiceException {
        ServiceException serviceException = getServiceException(cause, errorType, (Object)null);
        logError(logLevel, serviceException);
        throw serviceException;
    }

    /**
     *
     * Creates {@link ServiceException}.
     * Uses code and template string from {@link ErrorType} and in-place arguments to
     * construct exception message text.
     *
     *
     * @param errorType type of error
     * @param inplaceArgs in-place arguments to construct message text
     * @return
     */
    private static ServiceException getServiceException(ErrorType errorType, Object ... inplaceArgs) {

        String localizedMessage = getLocalizedMessage(errorType);

        return new ServiceException(MessageFormat.format(localizedMessage, inplaceArgs), errorType.getCode());
    }



    /**
     *
     * Creates {@link ServiceException}.
     * Uses code and template string from {@link ErrorType} and in-place arguments to
     * construct exception message text.
     *
     * @param cause
     * @param errorType type of error
     * @param inplaceArgs in-place arguments to construct message text
     * @return
     */
    private static ServiceException getServiceException(Throwable cause, ErrorType errorType, Object ... inplaceArgs) {

        String localizedMessage = getLocalizedMessage(errorType);

        return new ServiceException(MessageFormat.format(localizedMessage, inplaceArgs), cause, errorType.getCode());
    }

    private static String getLocalizedMessage(ErrorType errorType) {
        return errorType.getMessageEng();
    }

    private static void logError(ErrorLogLevel logLevel, ServiceException serviceException) {
        switch (logLevel){
            case DEBUG:
                log.debug(serviceException.getMessage(), serviceException);
                break;
            case INFO:
                log.info(serviceException.getMessage(), serviceException);
                break;
            case WARN:
                log.warn(serviceException.getMessage(), serviceException);
                break;
            default:
                log.error(serviceException.getMessage(), serviceException);
        }
    }
}
