package com.schedule.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.schedule.ApplicationContextAwareJob;
import com.schedule.service.TicketJobService;

public class SendTicketMailJob extends ApplicationContextAwareJob {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		 TicketJobService tkService = getBean(TicketJobService.class);
		tkService.sendOrderMail();
	}

}
