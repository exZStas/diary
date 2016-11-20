package com.vm62.diary.common;


/**
 * Error types usually used to create {@link ServiceException}
 * through {@link ExceptionFactory}.
 * Contains error codes and message template for in-place formatting.
 *
 * Attention! Be careful when editing templates, especially when
 * moving/adding/deleting in-place tags.
 * This could lead to generation of corrupted messages which already use these templates.
 */
public enum ErrorType {

    // General exceptions (00)
    CANNOT_BE_NULL("00-001", "{0} cannot be null."),
    CANNOT_BE_NULL_OR_EMPTY("00-002", "{0} cannot be null or empty."),
    CANNOT_ALL_BE_NULL_OR_EMPTY("00-003", "{0} cannot all be null or empty."),
    CANNOT_CALCULATE_VALUE("00-004", "{0} cannot calculate value"),

    //registration/login exception (01)
    PASSWORD_TOO_SHORT("01-001", "{0} password too short"),
    PASSWORD_MALFORMED("01-002", "{0} password malformed"),
    CANNOT_CREATE_USER_EMAIL_ALREADY_EXIST("01-003", "{0} email already exist"),
;


    /** 5-digit message code */
    private String code;

    /** message template in English*/
    private String messageEng;


    ErrorType(String code, String messageEng) {
        this.code = code;
        this.messageEng = messageEng;
    }


    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Returns error message according to langCode.
     * If langCode is null or not supported returns 'en' message by default.
     *
     * @return
     */
    public String getMessage() {
        return getMessageEng();
    }

    public String getMessageEng() {
        return messageEng;
    }

}
