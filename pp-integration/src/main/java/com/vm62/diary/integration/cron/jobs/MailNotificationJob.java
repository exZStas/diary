package com.vm62.diary.integration.cron.jobs;

import com.vm62.diary.backend.core.bean.MailNotificationBean;
import com.vm62.diary.common.ErrorType;
import com.vm62.diary.common.ExceptionFactory;
import com.vm62.diary.common.ServiceException;
import com.vm62.diary.common.utils.InjectorProvider;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

import static com.vm62.diary.common.ErrorType.CANNOT_SEND_MAIL;

@Slf4j
public class MailNotificationJob implements Job {
    private static final Long ATTEMPT = 1L;


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("mailNotificationJob has been started in " + new Date());
        MailNotificationBean mailNotificationBean = InjectorProvider.inject(MailNotificationBean.class);

        try {
            mailNotificationBean.sendRegistrationNotification();
        } catch (Exception e) {
            log.error("arose a error during sending a email");
        }
        log.info("mailNotificationJob stopped in " + new Date());

    }
}

