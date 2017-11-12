package com.schedule;

import org.quartz.Job;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public abstract class ApplicationContextAwareJob implements Job, ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public <T> T getBean(Class<T> clazz) {
		return applicationContext.getBean(clazz);
	}

	public <T> T getBean(String name, Class<T> clazz) {
		return applicationContext.getBean(name, clazz);
	}

}
