package com.datazord.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScheduleJob {	
	
	private static final Logger logger = LoggerFactory.getLogger(ScheduleJob.class);

	public ScheduleJob() {
		doJob();
	}

	private void doJob() {
		long timeInterval = 5 * 1000; // add to config.xml

		Runnable runnable = new Runnable() {
			public void run() {
				while (true) {
	
					System.out.println("Start Job");
					if(validateJobDate()){
						//put code here 
					}

					try {
						Thread.sleep(timeInterval);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		Thread thread = new Thread(runnable);
		thread.start();
	}

	private boolean validateJobDate() {

		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

			Date lastRunDate = dateFormat.parse("03/07/2018"); // get from DB
			Date currentDate = new Date();

			logger.info("Last Run Date >>",lastRunDate);
			
			String currentDateStr = dateFormat.format(currentDate);
			logger.info("Current Date >>",currentDateStr);

			Integer scheduleInterval = 5;// get from DB
			Calendar c = Calendar.getInstance();
			c.setTime(lastRunDate);
			c.add(Calendar.DATE, scheduleInterval);
			Date runDate = c.getTime();
			String runDateStr = dateFormat.format(runDate);
			logger.info("RunDate >>"+runDateStr);
			
			if (runDateStr.equals(currentDateStr)) {
				logger.info("Job should Start Now");
				return true;
			} else{
				logger.info("Job shouldn't Start Now");
				return false;
			}

		} catch (Exception e) {
			logger.error("", e);
			return false;
		}

	}

}
