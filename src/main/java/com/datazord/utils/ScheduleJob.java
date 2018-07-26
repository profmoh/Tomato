package com.datazord.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datazord.model.destination.DestinationCategories;
import com.datazord.repositories.DestinationCategoriesRepository;

import reactor.core.publisher.Flux;

@Service
public class ScheduleJob {

	private static final Logger logger = LoggerFactory.getLogger(ScheduleJob.class);

	long timeInterval;
	
	@Autowired
	private DestinationCategoriesRepository repository;

	
//	public ScheduleJob(long timeInterval) {
//		this.timeInterval = timeInterval;
//
//		//doJob();
//	}

	public void doJob(long timeInterval) {
		this.timeInterval = timeInterval;
		Runnable runnable = new Runnable() {
			public void run() {
				while (true) {
					System.out.println("Start Job");

					if (validateJobDate()) {
						Flux<DestinationCategories>f=repository.findAll();
						List<DestinationCategories>l=f.collectList().block();
						l.forEach(d->System.out.print(d));
						
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

	private static boolean validateJobDate() {

		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh");

			Date lastRunDate = dateFormat.parse("26/07/2018  6"); // get from DB
			Date currentDate = new Date();

			logger.info("Last Run Date >>", lastRunDate);

			String currentDateStr = dateFormat.format(currentDate);
			logger.info("Current Date >>", currentDateStr);

			Integer scheduleInterval = 5;// get from DB
			Calendar c = Calendar.getInstance();
			c.setTime(lastRunDate);
			c.add(Calendar.HOUR, scheduleInterval);
			Date runDate = c.getTime();
			String runDateStr = dateFormat.format(runDate);
			logger.info("RunDate >>" + runDateStr);

			if (runDateStr.equals(currentDateStr)) {
				logger.info("Job should Start Now");
				return true;
			} else {
				logger.info("Job shouldn't Start Now");
				return false;
			}

		} catch (Exception e) {
			logger.error("", e);
			return false;
		}

	}

}
