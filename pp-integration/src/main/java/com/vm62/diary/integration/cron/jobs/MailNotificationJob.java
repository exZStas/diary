package com.vm62.diary.integration.cron.jobs;

import com.vm62.diary.common.utils.InjectorProvider;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@Slf4j
public class MailNotificationJob implements Job {
    private static final Long ATTEMPT = 1L;


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        //MailNotificationBean mailNotificationBean = InjectorProvider.inject(MailNotificationBean.class);

    }
}

