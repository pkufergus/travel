package com.schedule.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.schedule.ApplicationContextAwareJob;
import com.schedule.service.MasterDataService;

public class CollectPriceJob extends ApplicationContextAwareJob  {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		MasterDataService mdService = getBean(MasterDataService.class);
		System.out.println("开始执行"+System.currentTimeMillis());
		mdService.collectMasterDataCount();
		System.out.println("执行结束"+System.currentTimeMillis());
	}

}
