package com.vm62.diary.common.constants;

public enum MailSubjects {
    REGISTRATION("Registration", "You are had registered on diary application! Please, approve your email via link that located below. ");

    private String value;
    private String content;
    MailSubjects(String subject, String content){
        this.value = subject;
        this.content = content;
    }

    public String getSubject(){
        return this.value;
    }

    public String getContent(){
        return this.content;
    }
}
