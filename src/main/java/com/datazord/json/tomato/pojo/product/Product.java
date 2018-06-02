package com.datazord.json.tomato.pojo.product;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

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
	
	@JsonProperty("product_related")
	private List<String> product_related=new ArrayList<>();
	
	@JsonProperty("product_filter")
	private List<String> product_filter=new ArrayList<>();
	
	@JsonProperty("product_category")
	private List<String> product_category=new ArrayList<>();
	
	@JsonProperty("product_description")
	private List<ProductDescription> product_description=new ArrayList<>();
	
	@JsonProperty("product_special")
	private List<ProductSpecial>product_special=new ArrayList<>();

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getTax_class_id() {
		return tax_class_id;
	}

	public void setTax_class_id(String tax_class_id) {
		this.tax_class_id = tax_class_id;
	}

	public String getManufacturer_id() {
		return manufacturer_id;
	}

	public void setManufacturer_id(String manufacturer_id) {
		this.manufacturer_id = manufacturer_id;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Integer getReward() {
		return reward;
	}

	public void setReward(Integer reward) {
		this.reward = reward;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<String> getOther_images() {
		return other_images;
	}

	public void setOther_images(List<String> other_images) {
		this.other_images = other_images;
	}

	public String getShipping() {
		return shipping;
	}

	public void setShipping(String shipping) {
		this.shipping = shipping;
	}

	public Integer getStock_status_id() {
		return stock_status_id;
	}

	public void setStock_status_id(Integer stock_status_id) {
		this.stock_status_id = stock_status_id;
	}

	public String getUpc() {
		return upc;
	}

	public void setUpc(String upc) {
		this.upc = upc;
	}

	public String getEan() {
		return ean;
	}

	public void setEan(String ean) {
		this.ean = ean;
	}

	public String getJan() {
		return jan;
	}

	public void setJan(String jan) {
		this.jan = jan;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getMpn() {
		return mpn;
	}

	public void setMpn(String mpn) {
		this.mpn = mpn;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDate_available() {
		return date_available;
	}

	public void setDate_available(String date_available) {
		this.date_available = date_available;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getWeight_class_id() {
		return weight_class_id;
	}

	public void setWeight_class_id(Integer weight_class_id) {
		this.weight_class_id = weight_class_id;
	}

	public Integer getLenght() {
		return lenght;
	}

	public void setLenght(Integer lenght) {
		this.lenght = lenght;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getLenght_class_id() {
		return lenght_class_id;
	}

	public void setLenght_class_id(Integer lenght_class_id) {
		this.lenght_class_id = lenght_class_id;
	}

	public Integer getSubtract() {
		return subtract;
	}

	public void setSubtract(Integer subtract) {
		this.subtract = subtract;
	}

	public Integer getMinimum() {
		return minimum;
	}

	public void setMinimum(Integer minimum) {
		this.minimum = minimum;
	}

	public String getSort_order() {
		return sort_order;
	}

	public void setSort_order(String sort_order) {
		this.sort_order = sort_order;
	}

	public List<String> getProduct_related() {
		return product_related;
	}

	public void setProduct_related(List<String> product_related) {
		this.product_related = product_related;
	}

	public List<String> getProduct_filter() {
		return product_filter;
	}

	public void setProduct_filter(List<String> product_filter) {
		this.product_filter = product_filter;
	}

	public List<String> getProduct_category() {
		return product_category;
	}

	public void setProduct_category(List<String> product_category) {
		this.product_category = product_category;
	}

	public List<ProductDescription> getProduct_description() {
		return product_description;
	}

	public void setProduct_description(List<ProductDescription> product_description) {
		this.product_description = product_description;
	}

	public List<ProductSpecial> getProduct_special() {
		return product_special;
	}

	public void setProduct_special(List<ProductSpecial> product_special) {
		this.product_special = product_special;
	}
	
}
