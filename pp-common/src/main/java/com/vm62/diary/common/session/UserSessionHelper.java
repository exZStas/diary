package com.vm62.diary.common.session;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.vm62.diary.common.utils.ContextUtil;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpSession;

/**
 * This singleton provides methods to work with user session
 */
@Slf4j @ToString
@Singleton
public class UserSessionHelper {

    @Inject
    private ContextUtil contextUtil;

    private ThreadLocal<HttpSession> httpSessionThreadLocal = new ThreadLocal<>();

    protected UserSessionHelper() {
    }

    public void createUserSession(Long userId) {
        removeSessionAttributes();

        getSession().setAttribute(SessionAttribute.SESSION_AUTH_TOKEN, "authorized");
        getSession().setAttribute(SessionAttribute.USER_ID, userId);



        try {
            Integer userSessionTimeout = contextUtil.getUserSessionTimeout();
            getSession().setMaxInactiveInterval(userSessionTimeout);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        }

        log.info("New session created id=" + getSession().getId() + " maxIdleTimeout=" + getSession().getMaxInactiveInterval());
    }

    public void createAdminUserSession(Long adminId) {

        getSession().setAttribute(SessionAttribute.SESSION_AUTH_TOKEN, "authorized");
        getSession().setAttribute(SessionAttribute.ADMIN_ID, adminId);
        getSession().setAttribute(SessionAttribute.ADMIN, "ADMIN");
    }
    public void createAdminUserSession(Long adminId, Long practiceId) {
        getSession().setAttribute(SessionAttribute.SESSION_AUTH_TOKEN, "authorized");
        getSession().setAttribute(SessionAttribute.ADMIN_ID, adminId);
        getSession().setAttribute(SessionAttribute.ADMIN, "ADMIN");
    }

    public void removeAdminSessionAttributes() {
        getSession().removeAttribute(SessionAttribute.ADMIN);
        getSession().removeAttribute(SessionAttribute.ADMIN_ID);
        getSession().removeAttribute(SessionAttribute.SESSION_AUTH_TOKEN);
    }

    private void removeSessionAttributes() {
        getSession().removeAttribute(SessionAttribute.SESSION_AUTH_TOKEN);
        getSession().removeAttribute(SessionAttribute.USER_ID);
        getSession().removeAttribute(SessionAttribute.ADMIN);
        getSession().removeAttribute(SessionAttribute.ADMIN_ID);
    }

    public void setHttpSession(HttpSession httpSession) {
        httpSessionThreadLocal.set(httpSession);
    }

    private HttpSession getSession() {
        return httpSessionThreadLocal.get();
    }

    public void closeUserSession() {
        httpSessionThreadLocal.remove();
    }

    public void invalidateUserSession() {
        if (getSession() != null) {
            // catch IllegalStateException (in case when method is called on an already invalidated session)
            // it's possible, because session can be closed from websocket and rpc delegates.
            try {
                getSession().invalidate();
            } catch (Exception e) {
                log.debug(" There is error during invalidating user session " + e);
            }
        }
    }

    public boolean isAdmin() {
        if (getSession() == null) {
            return false;
        }
        Object attribute = getSession().getAttribute(SessionAttribute.ADMIN);
        return (attribute != null) && ("ADMIN".equals(attribute)) && (getSession().getAttribute(SessionAttribute.ADMIN_ID) != null);
    }

    public boolean isAuthorized() {
        if (getSession() == null) {
            return false;
        }
        Object attribute = getSession().getAttribute(SessionAttribute.SESSION_AUTH_TOKEN);
        return attribute != null;
    }

    public Long getUserId() {
        if (getSession() == null) {
            return null;
        }
        return (Long) getSession().getAttribute(SessionAttribute.USER_ID);
    }
}
