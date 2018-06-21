package com.datazord.model.source;

import javax.validation.constraints.NotBlank;

import org.springframework.data.mongodb.core.mapping.Document;

import com.datazord.model.BaseModel;
import com.datazord.model.source.SourceColor.SourceColorBuilder;

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
@Document(collection = "source_categories")
public class SourceCategories extends BaseModel{

	String id;
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String languageId;
}
