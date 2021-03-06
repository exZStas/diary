package com.vm62.diary.integration.core.injection;

import com.vm62.diary.integration.server.SecurityFilter;
import com.vm62.diary.integration.server.servlet.VerifyRegistrationServlet;
import com.vm62.diary.integration.server.rpc.RpcServices;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.ServletModule;

public class GuiceServletModule extends ServletModule {
    @Override
    protected void configureServlets() {
        install(new JpaPersistModule("DefaultPersistenceUnit"));
        filter("/*").through(SecurityFilter.class);
        // bind rcp services with impelentations
        for (RpcServices serviceImpl : RpcServices.values()) {
            serve(serviceImpl.getUrl()).with(serviceImpl.getImplClass());
        }

        serve(VerifyRegistrationServlet.URL_PATTERN).with(VerifyRegistrationServlet.class);

    }
}
