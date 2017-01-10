package com.vm62.diary.integration.cron.jobs;

import com.vm62.diary.backend.core.bean.EventBean;
import com.vm62.diary.common.ExceptionFactory;
import com.vm62.diary.common.utils.InjectorProvider;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@Slf4j
public class EventStatusWatcher implements Job{
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        EventBean eventBean = InjectorProvider.inject(EventBean.class);
        try{
            eventBean.processAddledEvents();
        } catch (Exception e){
            log.error("There is error during process addled events");
        }

    }
}
