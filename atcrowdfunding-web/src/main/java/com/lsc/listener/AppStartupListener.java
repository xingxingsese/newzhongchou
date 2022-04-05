package com.lsc.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @Description:
 * @Param:
 * @Author: lisc
 * @date:
 */
public class AppStartupListener implements ServletContextListener {

    private static final Logger log = LoggerFactory.getLogger(AppStartupListener.class);

    /**
     * 项目初始化
     *
     * @param sce
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // 代表整个项目, 这里封装了项目的所有信息
        ServletContext servletContext = sce.getServletContext();
        // 当前项目路径
        String contextPath = servletContext.getContextPath();
        servletContext.setAttribute("ctx", contextPath);

        log.debug("com.lsc.listener.AppStartupListener 项目启动... lsc");
    }

    /**
     * 项目销毁
     *
     * @param sce
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.debug("com.lsc.listener.AppStartupListener 项目关闭... lsc");
    }
}
