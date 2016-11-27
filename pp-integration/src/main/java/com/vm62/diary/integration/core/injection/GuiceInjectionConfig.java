package com.vm62.diary.integration.core.injection;

import com.vm62.diary.backend.core.bean.SessionBean;
import com.vm62.diary.common.utils.ContextUtil;
import com.vm62.diary.common.utils.InjectorProvider;
import com.vm62.diary.integration.cron.CronJobsConfigurator;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import com.google.inject.servlet.GuiceServletContextListener;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;

/**
 * Configure Guice for web applications.
 */
@Slf4j
public class GuiceInjectionConfig extends GuiceServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        log.info("Initializing Guice injection...");
        super.contextInitialized(servletContextEvent);

        log.info("Configuring InjectorProvider...");
        InjectorProvider.init((Injector) servletContextEvent.getServletContext().getAttribute(Injector.class.getName()));

        log.info("Starting PersistService...");
        InjectorProvider.inject(PersistService.class).start();


        log.info("Configuring cron jobs ...");
        CronJobsConfigurator cronJobsConfigurator = InjectorProvider.inject(CronJobsConfigurator.class);
        cronJobsConfigurator.configure();
        cronJobsConfigurator.start();

        //prepare mail session
        ContextUtil contextUtil = InjectorProvider.inject(ContextUtil.class);

        SessionBean sessionBean = InjectorProvider.inject(SessionBean.class);

        try{
            sessionBean.initSession(contextUtil.getSmtpHost(), contextUtil.getFromMailAddress(), contextUtil.getEmailPassword());
        } catch (Exception e) {
            log.debug("Failed to init mail session " + e);
        }


    }

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(
                new GuiceServletModule(),
                new GuiceMainModule()
        );
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        log.info("Stopping PersistService...");
        InjectorProvider.inject(PersistService.class).stop();

        log.info("Destroying Guice injector ...");
        super.contextDestroyed(servletContextEvent);

        log.info("Stopping cron jobs ...");
        CronJobsConfigurator cronJobsConfigurator = InjectorProvider.inject(CronJobsConfigurator.class);
        cronJobsConfigurator.stop();
    }
}