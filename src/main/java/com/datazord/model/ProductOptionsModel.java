package com.datazord.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.datazord.json.tomato.pojo.ProductOptions.OptionValueDescription;

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
@Document(collection = "product_options")
public class ProductOptionsModel extends BaseModel implements Serializable{
 

	private static final long serialVersionUID = -1766250022947976415L;

	private Integer option_id;
	
	private Integer option_value_id;
	
	private List<OptionValueDescription>optionValueDescriptions=new ArrayList<>();
	
    private String image;
	
	private String thumb;
	 
	private Integer sort_order;
}
