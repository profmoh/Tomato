package com.datazord.model;

import javax.validation.constraints.NotBlank;

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
@Document(collection ="company")
public class Company extends BaseModel  {

	@NotBlank
	private String companyName;
}
