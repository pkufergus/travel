package com.schedule.support;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class JobSupport {

	private static Logger logger = LoggerFactory.getLogger(JobSupport.class);

	public static void scheduleJob(Scheduler scheduler, String jobName, Class<? extends Job> jobClass, String cronExpression) {
		try {
			JobKey key = new JobKey(jobName, Scheduler.DEFAULT_GROUP);
			JobDetail jobDetail = scheduler.getJobDetail(key);
			if (jobDetail != null) {
				rescheduleOrScheduleJob(scheduler, jobDetail, cronExpression);
			} else {
				scheduleJob(scheduler, key, jobClass, cronExpression);
			}
		} catch (SchedulerException e) {
			//logger.error(e.getMessage(), e);
			logger.info("job...Exeption");
		} catch (ParseException e) {
			//logger.error(e.getMessage(), e);
			logger.info("job...Exeption");
		}
	}

	static void rescheduleOrScheduleJob(Scheduler scheduler, JobDetail jobDetail, String cronExpression) throws SchedulerException, ParseException {
		final JobKey jobKey = jobDetail.getKey();

		final List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobDetail.getKey());
		if (triggers.size() < 1) {
			scheduler.deleteJob(jobKey);
			scheduleJob(scheduler, jobKey, jobDetail.getJobClass(), cronExpression);
		} else {
			final Trigger trigger = triggers.get(0);
			if (trigger instanceof CronTrigger) {
				final CronTrigger cronTrigger = (CronTrigger) trigger;
				if (!cronTrigger.getCronExpression().equals(cronExpression)) {
					scheduler.rescheduleJob(cronTrigger.getKey(), newTrigger(triggerName(jobKey.getName()), cronExpression));
				}
			}
		}
	}

	static void scheduleJob(Scheduler scheduler, JobKey jobKey, Class<? extends Job> jobClass, String cronExpression) throws SchedulerException, ParseException {
		final JobDetail detail = newJob(jobClass).withIdentity(jobKey).requestRecovery(true).storeDurably(true).build();
		scheduler.scheduleJob(detail, newTrigger(triggerName(jobKey.getName()), cronExpression));
	}

	static Trigger newTrigger(String name, String cronExpression) throws ParseException {
		return TriggerBuilder.newTrigger().withIdentity(name, Scheduler.DEFAULT_GROUP)
				.withSchedule(cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing()).build();
	}

	static String triggerName(String prefix) {
		return new StringBuilder(prefix).append("_trigger_").append(UUID.randomUUID().toString()).toString();
	}
}
