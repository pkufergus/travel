package com.schedule;

import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.simpl.SimpleJobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextAwareJobFactory extends SimpleJobFactory {

	@Autowired
	private ApplicationContext applicationContext;

	@Override
	public Job newJob(TriggerFiredBundle bundle, Scheduler scheduler) throws SchedulerException {
		Job job = super.newJob(bundle, scheduler);

		if (job instanceof ApplicationContextAwareJob) {
			((ApplicationContextAwareJob) job).setApplicationContext(applicationContext);
		}

		return job;
	}

}
