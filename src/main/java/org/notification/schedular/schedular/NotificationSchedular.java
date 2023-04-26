package org.notification.schedular.schedular;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.notification.schedular.service.SchedularService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/*
 * It is scheduler class.
 * It will trigger process as per cron expression
 */
@Component
public class NotificationSchedular {

	private static Logger logger = LogManager.getLogger(NotificationSchedular.class);

	@Autowired
	private SchedularService schedularService;

	/*
	 * Method to trigger process.
	 * 
	 * @@Scheduled helps to decide time interval
	 */
	@Scheduled(cron = "0 * * * * *")
	public void execute() {
		logger.info("NotificationSchedular : execute()");
		schedularService.checkAndTriggerNotification();
	}
}
