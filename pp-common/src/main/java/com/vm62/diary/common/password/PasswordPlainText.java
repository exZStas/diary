package com.vm62.diary.common.password;

import com.vm62.diary.common.ErrorType;
import com.vm62.diary.common.ServiceException;
import com.vm62.diary.common.utils.ValidationUtils;

import static com.vm62.diary.common.utils.CryptUtils.md5;

public class PasswordPlainText extends Password {
    private String passwordPlainText;
    private String login;

    public PasswordPlainText(String passwordPlainText){
        this.passwordPlainText = passwordPlainText;
    }

    public PasswordPlainText(String passwordPlainText, String login){
        this.passwordPlainText = passwordPlainText;
        this.login = login;
    }
    public void setLogin (String login){
        this.login = login;
    }

    @Override
    public void validate() throws ServiceException {
        ValidationUtils.ifNullOrEmpty(passwordPlainText, ErrorType.CANNOT_ALL_BE_NULL_OR_EMPTY, "passwordPlainText");
        ValidationUtils.ifTooShort(passwordPlainText, 8, ErrorType.PASSWORD_TOO_SHORT, 8);
        ValidationUtils.ifPasswordIncorrectFormat(passwordPlainText, login, ErrorType.PASSWORD_MALFORMED);
    }

    @Override
    public String getAsString() {
        return this.passwordPlainText;
    }

    @Override
    public PasswordEncoded encode() throws ServiceException {
        return new PasswordEncoded(md5(passwordPlainText));
    }
}
