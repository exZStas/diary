package com.vm62.diary.integration.server;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.vm62.diary.common.session.UserSessionHelper;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Security filter usually using for protection check, so only authorize users may access to system.
 * Now it using for setting http session for user. Extend if necessary.
 */
@Slf4j
@Singleton
public class SecurityFilter implements Filter {

    @Inject
    UserSessionHelper userSessionHelper;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        //initialize user session
        userSessionHelper.setHttpSession(httpRequest.getSession());
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
