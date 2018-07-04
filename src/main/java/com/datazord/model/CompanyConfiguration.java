package com.datazord.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Document(collection ="company_configuration")
public class CompanyConfiguration extends BaseModel  {

	
	private String imagePath;
	
	private String xmlPath;
	
	private Integer scheduleRunTime;
	private String companyId;
	
	
}
