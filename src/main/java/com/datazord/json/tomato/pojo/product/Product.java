package com.datazord.json.tomato.pojo.product;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product {

	@JsonProperty("model")
	private String model;
	
	@JsonProperty("quantity")
	private String quantity;
	
	@JsonProperty("price")
	private String price;
	
	@JsonProperty("tax_class_id")
	private String tax_class_id;
	
	@JsonProperty("manufacturer_id")
	private String manufacturer_id;
	
	@JsonProperty("sku")
	private String sku;
	
	@JsonProperty("keyword")
	private String keyword;
	
	@JsonProperty("status")
	private String status;
	
	@JsonProperty("points")
	private Integer points=0;
	
	@JsonProperty("reward")
	private Integer reward=0;

	@JsonProperty("image")
	private String image;

	@JsonProperty("other_images")
	private List<String> other_images=new ArrayList<>();
	
	@JsonProperty("shipping")
	private String shipping;
	
	@JsonProperty("stock_status_id")
	private Integer stock_status_id=0;
	
	@JsonProperty("upc")
	private String upc;
	
	@JsonProperty("ean")
	private String ean;
	
	@JsonProperty("jan")
	private String jan;
	
	@JsonProperty("isbn")
	private String isbn;
	
	@JsonProperty("mpn")
	private String mpn;
	
	@JsonProperty("location")
	private String location;
	
	@JsonProperty("date_available")
	private String date_available;
	
	@JsonProperty("weight")
	private Integer weight=0;
	
	@JsonProperty("weight_class_id")
	private Integer weight_class_id=0;
	
	@JsonProperty("lenght")
	private Integer lenght=0;
	
	@JsonProperty("width")
	private Integer width=0;
	
	@JsonProperty("height")
	private Integer height=0;
	
	@JsonProperty("lenght_class_id")
	private Integer lenght_class_id=0;
	
	@JsonProperty("subtract")
	private Integer subtract=0;
	
	@JsonProperty("minimum")
	private Integer minimum=0;
	 
	@JsonProperty("sort_order")
	private String sort_order;
	
	@JsonProperty("product_store")
	private List<String>product_store=new ArrayList<>();
	
	@JsonProperty("product_related")
	private List<String> product_related=new ArrayList<>();
	
	@JsonProperty("product_filter")
	private List<String> product_filter=new ArrayList<>();
	
	@JsonProperty("product_description")
	private List<ProductDescription> product_description=new ArrayList<>();
	
	@JsonProperty("product_category")
	private List<String> product_category=new ArrayList<>();
	
	@JsonProperty("cv_required")
	private String cv_required;
	
	@JsonProperty("cv_color_option_id")
	private String cv_color_option_id;
	
	@JsonProperty("cv_size_option_id")
	private String cv_size_option_id;
	
	@JsonProperty("product_custom_option")
	private List<productCustomOption>product_custom_option=new ArrayList<>();
	
}
