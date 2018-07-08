package com.datazord.form;

public class ConfigurationForm extends BaseForm {

	private static final long serialVersionUID = -6303868470623269528L;

	private String companyName;
	
	private String imagePath;
	
	private String xmlPath;
	
	private String filePath;
	
	private Integer scheduleRunTime;
	

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getXmlPath() {
		return xmlPath;
	}

	public void setXmlPath(String xmlPath) {
		this.xmlPath = xmlPath;
	}

	public Integer getScheduleRunTime() {
		return scheduleRunTime;
	}

	public void setScheduleRunTime(Integer scheduleRunTime) {
		this.scheduleRunTime = scheduleRunTime;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
