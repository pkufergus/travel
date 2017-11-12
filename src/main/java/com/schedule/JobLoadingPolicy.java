package com.schedule;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.schedule.job.CollectPriceJob;
import com.schedule.job.SendTicketMailJob;
import com.schedule.support.JobSupport;


public class JobLoadingPolicy implements Runnable {

	private static final Logger logger = LoggerFactory
			.getLogger(JobLoadingPolicy.class);

	private Scheduler scheduler;
	private PropertiesConfiguration configuration;

	public JobLoadingPolicy(Scheduler scheduler,
			ApplicationContext applicationContext) {
		this.scheduler = scheduler;

		initConfiguration();
	}

	void initConfiguration() {
		try {
			configuration = new PropertiesConfiguration("cron.properties");
			configuration
					.setReloadingStrategy(new FileChangedReloadingStrategy());
		} catch (ConfigurationException e) {
			throw new IllegalStateException(
					"the configuration initialization failed.", e);
		}
	}

	void reloadConfiguration() {
		configuration.reload();
	}

	@Override
	public void run() {
		logger.info("JobLoadingPolicy.run()");
		try {
			reloadConfiguration();
			JobSupport.scheduleJob(scheduler,
					SendTicketMailJob.class.getName(),
					SendTicketMailJob.class,
					configuration.getString("cron.ticketmail"));
			
			JobSupport.scheduleJob(scheduler,
					CollectPriceJob.class.getName(),
					CollectPriceJob.class,
					configuration.getString("cron.collectprice"));

//			JobSupport.scheduleJob(scheduler,
//					CollectBaseDataJob.class.getName(),
//					CollectBaseDataJob.class,
//					configuration.getString("cron.basedata"));

			// JobSupport.scheduleJob(scheduler, RunOnceJob.class.getName(),
			// RunOnceJob.class, configuration.getString("cron.oncejob"));
		} catch (Exception e) {
			logger.info("job......Exception");
			e.printStackTrace();
		}
	}

}
