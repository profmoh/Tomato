package com.datazord.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datazord.api.service.TomatoServiceImpl;
import com.datazord.exceptions.MissedMappingException;
import com.datazord.model.CompanyConfiguration;
import com.datazord.repositories.CompanyConfigurationRepository;

@Service
public class ScheduleJob {

	private static final Logger logger = LoggerFactory.getLogger(ScheduleJob.class);

	public static long timeInterval = 5;

	@Autowired
	private TomatoServiceImpl apiService;

	@Autowired
	private CompanyConfigurationRepository repository;

	public void doJob() {
		CompanyConfiguration companyConfig = repository.findAll().blockFirst();
		ScheduleJob.timeInterval = companyConfig.getScheduleRunTime();

		Runnable runnable = new Runnable() {
			public void run() {
				while (true) {
					System.out.println("Start Job");

					try {
						apiService.saveProductListToAPI(null, companyConfig.getXmlPath());
					} catch (MissedMappingException me) {
						logger.error("", me);
					} catch (Exception e) {
						logger.error("", e);
					}

					try {
						Thread.sleep(timeInterval * 60 * 60 * 1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};

		Thread thread = new Thread(runnable);
		thread.start();
	}
}
