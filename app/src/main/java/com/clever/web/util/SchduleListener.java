package com.clever.web.util;


import com.clever.common.service.TableManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Randy on 2016/12/22.
 */
public class SchduleListener implements ServletContextListener {

    private Logger logger = LoggerFactory.getLogger(SchduleListener.class);

    private int task_period = 5*60*1000;
//    @Autowired
    private TableManageService tableManageService;
//    ServletContext servletContext = this.getServletContext();
//    WebApplicationContext context =
//            WebApplicationContextUtils.getWebApplicationContext(servletContext);
//    private TableManageService tableManageService = (TableManageService) context.getBean("tableManageService");


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Timer timer = new Timer();
        WebApplicationContext context =
                WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext());
        tableManageService = (TableManageService) context.getBean("TableManageService");
        timer.schedule(new TimerTask(){
            @Override
            public void run() {
                tableManageService.updateRunFlag();
            }
        },0,task_period);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}


