package com.vm62.diary.integration.server.servlet;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.vm62.diary.backend.core.bean.UserBean;
import com.vm62.diary.backend.core.entities.User;
import com.vm62.diary.common.ServiceException;
import com.vm62.diary.common.utils.ContextUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Timer;
import java.util.TimerTask;

import static com.vm62.diary.common.constants.GetParameters.REGISTRATION_ID;

@Slf4j
@Singleton
public class VerifyRegistrationServlet extends HttpServlet {

    public static final String URL_PATTERN = "/verifyRegistration";

    @Inject
    private UserBean userBean;
    @Inject
    private ContextUtil contextUtil;

    private User user;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String registrationId = req.getParameter(REGISTRATION_ID.getParameter());
        PrintWriter out = resp.getWriter();

        try {
            user = userBean.getUserByRegistrationId(registrationId);
        } catch (ServiceException e) {
            log.debug("Cannot get user by registration id " + e);
        }

        if(user != null){
            user.setRegister(true);
            try {
                userBean.updateUser(user);
            } catch (ServiceException e) {
                log.debug("Cannot update user " + e);
            }
        }

        notifyUserAboutSuccessfulRegistration(out);

        try {
            resp.sendRedirect(contextUtil.getApplicationURL());
        } catch (Exception e) {
            log.debug("Cannot redirect to main page!");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    private void notifyUserAboutSuccessfulRegistration(PrintWriter out) throws IOException {
        TimerTask timerTask = new DelayTimer();
        Timer timer = new Timer();
        out.write("Registration was successful!");
        timer.schedule(timerTask, 50000L);
        timerTask.cancel();
        timer.cancel();
    }

    private class DelayTimer extends TimerTask {

        @Override
        public void run() {
            try {
                Thread.sleep(50000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
