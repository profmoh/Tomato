
package com.datazord.json.tomato.pojo.categories;

import java.util.HashMap;
import java.util.Map;

import com.datazord.deserializers.CustomDataDeserializer;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "category_id", "name", "description", "sort_order", "meta_title", "meta_description",
		"meta_keyword", "language_id", "image", "original_image", "categories" })
public class Category {

	@JsonProperty("category_id")
	private Long categoryId;

	@JsonProperty("parent_id")
	private Long parentId;

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
	private Integer languageId;

	@JsonProperty("image")
	private String image;

	@JsonProperty("original_image")
	private String originalImage;

	@JsonProperty("categories")
	@JsonDeserialize(using = CustomDataDeserializer.class)
	private Data categories;

	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
	public Category(Long categoryId, Long parentId, String name, String description, String sortOrder, String metaTitle,
			String metaDescription, String metaKeyword, Integer languageId, String image, String originalImage,
			Data categories) {
		super();

		this.categoryId = categoryId;
		this.parentId = parentId;
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
	public Long getCategoryId() {
		return categoryId;
	}

	@JsonProperty("category_id")
	public void setCategoryId(Long categoryId) {
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

	@JsonProperty("image")
	public String getImage() {
		return image;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
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
	public Data getCategories() {
		return categories;
	}

	@JsonProperty("categories")
	public void setCategories(Data categories) {
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
