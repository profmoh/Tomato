
package com.datazord.json.tomato.pojo.categories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "category_id", "name", "description", "sort_order", "meta_title", "meta_description",
		"meta_keyword", "language_id", "image", "original_image", "categories" })
public class Category {

	@JsonProperty("category_id")
	private String categoryId;

	@JsonProperty("name")
	private String name;

	@JsonProperty("description")
	private String description;

	@JsonProperty("sort_order")
	private String sortOrder;

	@JsonProperty("meta_title")
	private String metaTitle;

	@JsonProperty("meta_description")
	private String metaDescription;

	@JsonProperty("meta_keyword")
	private String metaKeyword;

	@JsonProperty("language_id")
	private String languageId;

	@JsonProperty("image")
	private String image;

	@JsonProperty("original_image")
	private String originalImage;

	@JsonProperty("categories")
	private List<Object> categories = null;

	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public Category() {
	}

	/**
	 * 
	 * @param originalImage
	 * @param sortOrder
	 * @param description
	 * @param name
	 * @param categoryId
	 * @param metaDescription
	 * @param image
	 * @param categories
	 * @param metaKeyword
	 * @param metaTitle
	 * @param languageId
	 */
	public Category(String categoryId, String name, String description, String sortOrder, String metaTitle,
			String metaDescription, String metaKeyword, String languageId, String image, String originalImage,
			List<Object> categories) {
		super();

		this.categoryId = categoryId;
		this.name = name;
		this.description = description;
		this.sortOrder = sortOrder;
		this.metaTitle = metaTitle;
		this.metaDescription = metaDescription;
		this.metaKeyword = metaKeyword;
		this.languageId = languageId;
		this.image = image;
		this.originalImage = originalImage;
		this.categories = categories;
	}

	@JsonProperty("category_id")
	public String getCategoryId() {
		return categoryId;
	}

	@JsonProperty("category_id")
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	@JsonProperty("description")
	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty("sort_order")
	public String getSortOrder() {
		return sortOrder;
	}

	@JsonProperty("sort_order")
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	@JsonProperty("meta_title")
	public String getMetaTitle() {
		return metaTitle;
	}

	@JsonProperty("meta_title")
	public void setMetaTitle(String metaTitle) {
		this.metaTitle = metaTitle;
	}

	@JsonProperty("meta_description")
	public String getMetaDescription() {
		return metaDescription;
	}

	@JsonProperty("meta_description")
	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}

	@JsonProperty("meta_keyword")
	public String getMetaKeyword() {
		return metaKeyword;
	}

	@JsonProperty("meta_keyword")
	public void setMetaKeyword(String metaKeyword) {
		this.metaKeyword = metaKeyword;
	}

	@JsonProperty("language_id")
	public String getLanguageId() {
		return languageId;
	}

	@JsonProperty("language_id")
	public void setLanguageId(String languageId) {
		this.languageId = languageId;
	}

	@JsonProperty("image")
	public String getImage() {
		return image;
	}

	@JsonProperty("image")
	public void setImage(String image) {
		this.image = image;
	}

	@JsonProperty("original_image")
	public String getOriginalImage() {
		return originalImage;
	}

	@JsonProperty("original_image")
	public void setOriginalImage(String originalImage) {
		this.originalImage = originalImage;
	}

	@JsonProperty("categories")
	public List<Object> getCategories() {
		return categories;
	}

	@JsonProperty("categories")
	public void setCategories(List<Object> categories) {
		this.categories = categories;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}
}
