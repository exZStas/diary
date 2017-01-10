package com.vm62.diary.integration.cron;

import com.vm62.diary.integration.cron.jobs.EventStatusWatcher;
import com.vm62.diary.integration.cron.jobs.MailNotificationJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@Slf4j
public class CronJobsConfigurator {
    private Scheduler scheduler;

    public void configure() {
        try {
            log.info("Starting cron jobs");
            scheduler = new StdSchedulerFactory().getScheduler();

            JobDetail mailNotificationJob = newJob(MailNotificationJob.class)
                    .withIdentity("MAIL_NOTIFICATION_JOB", "MAIL_NOTIFICATION_GROUP")
                    .build();
            Trigger mailNotificationTrigger = newTrigger()
                    .withIdentity("MAIL_NOTIFICATION_TRIGGER", "MAIL_NOTIFICATION_GROUP")
                    .startNow()
                    .withSchedule(simpleSchedule()
                            .withIntervalInMinutes(1)
                            .repeatForever())
                    .build();
            scheduler.scheduleJob(mailNotificationJob, mailNotificationTrigger);

            JobDetail eventStatusWatcher = newJob(EventStatusWatcher.class)
                    .withIdentity("EVENT_STATUS_JOB", "EVENT_STATUS_GROUP")
                    .build();
            Trigger eventStatusTrigger = newTrigger()
                    .withIdentity("EVENT_STATUS_TRIGGER", "EVENT_STATUS_GROUP")
                    .startNow()
                    .withSchedule(simpleSchedule()
                            .withIntervalInMinutes(1)
                            .repeatForever())
                    .build();
            scheduler.scheduleJob(eventStatusWatcher, eventStatusTrigger);

            log.info("Job and trigger initialize successfully.");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void start() {
        if (scheduler == null) {
            log.error("Scheduler is null");
            return;
        }

        try {
            scheduler.start();
            log.info("Scheduler started.");
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
        }
    }

    public void stop() {
        if (scheduler == null) {
            log.error("Scheduler is null");
            return;
        }

        try {
            log.info("Scheduler stopping with job complete...");
            scheduler.shutdown(true);
            log.info("Scheduler stopped.");
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
        }
    }
}
