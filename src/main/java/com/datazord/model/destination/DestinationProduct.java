package com.datazord.model.destination;

import org.springframework.data.mongodb.core.mapping.Document;

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
@Document(collection = "destination_product")
public class DestinationProduct extends BaseModel {

	private String id;
	private String name;

}
