package com.vm62.diary.integration.core.injection;

import com.vm62.diary.integration.server.rpc.RpcServices;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.ServletModule;

public class GuiceServletModule extends ServletModule {
    @Override
    protected void configureServlets() {
        install(new JpaPersistModule("DefaultPersistenceUnit"));
        // bind rcp services with impelentations
        for (RpcServices serviceImpl : RpcServices.values()) {
            serve(serviceImpl.getUrl()).with(serviceImpl.getImplClass());
        }


    }
}
