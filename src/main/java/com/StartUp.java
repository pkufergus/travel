package com;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.xml.XmlConfiguration;
import org.quartz.ListenerManager;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.listeners.BroadcastSchedulerListener;
import org.quartz.spi.JobFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.schedule.JobLoadingPolicy;
import com.schedule.SpringConfigure;





public class StartUp {
	public static void main(String[] args) throws Exception {
		startupJob();
		Resource resource = Resource.newClassPathResource("jetty.xml");
		XmlConfiguration configuration = new XmlConfiguration(
				resource.getInputStream());
		Server server = (Server) configuration.configure();
		startupJob();
		server.start();
		server.join();
		//startupJob();
	}
	static final void startupJob() throws SchedulerException {

		final ScheduledExecutorService scheduledExecutorService = Executors
				.newScheduledThreadPool(2);

		final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
				SpringConfigure.class);
		final JobFactory jobFactory = context.getBean(JobFactory.class);

		final StdSchedulerFactory factory = new StdSchedulerFactory();
		final Scheduler scheduler = factory.getScheduler();
		scheduler.setJobFactory(jobFactory);
		scheduler.start();

		scheduledExecutorService.scheduleWithFixedDelay(new JobLoadingPolicy(
				scheduler, context), 0, 30, TimeUnit.SECONDS);

		final ListenerManager listenerManager = scheduler.getListenerManager();
		listenerManager.addSchedulerListener(new BroadcastSchedulerListener() {
			@Override
			public void schedulerShutdown() {
				//logger.info("SchedulerListener.schedulerShutdown()");
				super.schedulerShutdown();
				context.close();
				scheduledExecutorService.shutdown();
			}
		});

	}
}
