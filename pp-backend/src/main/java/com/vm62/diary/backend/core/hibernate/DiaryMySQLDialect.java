package com.vm62.diary.backend.core.hibernate;

import org.hibernate.dialect.MySQLDialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;


public class DiaryMySQLDialect extends MySQLDialect{
    public DiaryMySQLDialect() {
        super();
        /**
         * Function to evaluate regexp in MySQL
         */
        registerFunction("regexp", new SQLFunctionTemplate(StandardBasicTypes.BOOLEAN, "?1 regexp ?2"));
    }
}
