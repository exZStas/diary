package com.vm62.diary.integration.server.rpc;

import javax.servlet.http.HttpServlet;

import static com.vm62.diary.frontend.client.service.ServletMappingConstants.LOGIN_SERVICE_URL;
import static com.vm62.diary.frontend.client.service.ServletMappingConstants.USER_SERVICE_URL;
import static com.vm62.diary.frontend.client.service.ServletMappingConstants.EVENT_SERVICE_URL;

/**
 * Binding rpc (gwt) services with implementations (used to bind in Guice)
 */
public enum RpcServices {
    USER_SERVICE(USER_SERVICE_URL, UserServiceImpl.class),
    LOGIN_SERVICE(LOGIN_SERVICE_URL, LoginServiceImpl.class),
    EVENT_SERVICE(EVENT_SERVICE_URL, EventServiceImpl.class);

    private String url;
    private Class<? extends HttpServlet> implClass;

    RpcServices(String url, Class<? extends HttpServlet> implClass) {
        this.url = url;
        this.implClass = implClass;
    }

    public String getUrl() {
        return url;
    }

    public Class<? extends HttpServlet> getImplClass() {
        return implClass;
    }
}
