package com.datazord.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import com.datazord.json.tomato.pojo.ProductOptions.OptionValue;
import com.datazord.model.User.UserBuilder;
import com.fasterxml.jackson.annotation.JsonProperty;

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
@Document(collection = "productOptions")
public class ProductOptionsModel extends BaseModel implements Serializable{
 

	private static final long serialVersionUID = -1766250022947976415L;

	private Integer option_id;

	private String name;
	
	private String type;
	
	private Integer sort_order;
	
	private List<OptionValue> option_values=new ArrayList<>();
}
