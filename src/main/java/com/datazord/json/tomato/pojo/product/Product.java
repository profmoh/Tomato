package com.datazord.json.tomato.pojo.product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.datazord.constants.TomatoConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product {

	@JsonProperty("model")
	public String model;

	@JsonProperty("quantity")
	public String quantity = "0";

	@JsonProperty("price")
	public String price = "0";

	@JsonProperty("tax_class_id")
	public String tax_class_id = "1";

	@JsonProperty("manufacturer_id")
	public String manufacturer_id = "8";

	@JsonProperty("sku")
	public String sku = "demo sku";

	@JsonProperty("keyword")
	public String keyword = "";

	@JsonProperty("status")
	public String status = "1";

	@JsonProperty("points")
	public Integer points = 0;

	@JsonProperty("reward")
	public Integer reward = 0;

	@JsonProperty("image")
	public String image;

	@JsonProperty("other_images")
	public List<String> other_images = new ArrayList<>();

	@JsonProperty("shipping")
	public String shipping = "1";

	@JsonProperty("stock_status_id")
	public Integer stock_status_id = 0;

	@JsonProperty("upc")
	public String upc = "";

	@JsonProperty("ean")
	public String ean = "";

	@JsonProperty("jan")
	public String jan = "";

	@JsonProperty("isbn")
	public String isbn = "";

	@JsonProperty("mpn")
	public String mpn = "";

	@JsonProperty("location")
	public String location = "";

	@JsonProperty("date_available")
	public String date_available;

	@JsonProperty("weight")
	public Integer weight = 0;

	@JsonProperty("weight_class_id")
	public Integer weight_class_id = 0;

	@JsonProperty("length")
	public Integer length = 0;

	@JsonProperty("width")
	public Integer width = 0;

	@JsonProperty("height")
	public Integer height = 0;

	@JsonProperty("length_class_id")
	public Integer length_class_id = 0;

	@JsonProperty("subtract")
	public Integer subtract = 0;

	@JsonProperty("minimum")
	public Integer minimum = 0;

	@JsonProperty("sort_order")
	public String sort_order = "1";

	@JsonProperty("product_store")
	public List<String> product_store = Arrays.asList("0");

	@JsonProperty("product_related")
	public List<String> product_related = new ArrayList<>();

	@JsonProperty("product_filter")
	public List<String> product_filter = new ArrayList<>();

	@JsonProperty("product_description")
	public List<ProductDescription> product_description = new ArrayList<>();

	@JsonProperty("product_category")
	public List<String> product_category = new ArrayList<>();

	@JsonProperty("cv_required")
	public String cv_required = "1";

	@JsonProperty("cv_color_option_id")
	public String cv_color_option_id = TomatoConstants.COLOR_PRODUCT_OPTION.toString();

	@JsonProperty("cv_size_option_id")
	public String cv_size_option_id = TomatoConstants.SIZE_PRODUCT_OPTION.toString();

	@JsonProperty("product_custom_option")
	public List<productCustomOption> product_custom_option = new ArrayList<>();

}
