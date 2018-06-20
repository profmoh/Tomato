package com.datazord.model.destination;

import javax.validation.constraints.NotBlank;

import org.springframework.data.mongodb.core.mapping.Document;

import com.datazord.enums.Language;
import com.datazord.model.BaseModel;

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
@Document(collection = "destination_size")
public class DestinationSize extends BaseModel {
	
	private String id;
	
	@NotBlank
	private String name;
	
	
	@NotBlank
	private String languageId ;
	
}
