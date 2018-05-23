
package com.datazord.json.tomato.pojo.login;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "user_id", "user_group_id", "username", "firstname", "lastname", "email", "image", "ip", "status",
		"date_added", "user_group" })
public class Data {

	@JsonProperty("user_id")
	private String userId;

	@JsonProperty("user_group_id")
	private String userGroupId;

	@JsonProperty("username")
	private String username;

	@JsonProperty("firstname")
	private String firstname;

	@JsonProperty("lastname")
	private String lastname;

	@JsonProperty("email")
	private String email;

	@JsonProperty("image")
	private String image;

	@JsonProperty("ip")
	private String ip;

	@JsonProperty("status")
	private String status;

	@JsonProperty("date_added")
	private String dateAdded;

	@JsonProperty("user_group")
	private String userGroup;

	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public Data() {
	}

	/**
	 * 
	 * @param username
	 * @param status
	 * @param email
	 * @param userGroup
	 * @param userId
	 * @param dateAdded
	 * @param image
	 * @param lastname
	 * @param userGroupId
	 * @param firstname
	 * @param ip
	 */
	public Data(String userId, String userGroupId, String username, String firstname, String lastname, String email,
			String image, String ip, String status, String dateAdded, String userGroup) {
		super();

		this.userId = userId;
		this.userGroupId = userGroupId;
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.image = image;
		this.ip = ip;
		this.status = status;
		this.dateAdded = dateAdded;
		this.userGroup = userGroup;
	}

	@JsonProperty("user_id")
	public String getUserId() {
		return userId;
	}

	@JsonProperty("user_id")
	public void setUserId(String userId) {
		this.userId = userId;
	}

	@JsonProperty("user_group_id")
	public String getUserGroupId() {
		return userGroupId;
	}

	@JsonProperty("user_group_id")
	public void setUserGroupId(String userGroupId) {
		this.userGroupId = userGroupId;
	}

	@JsonProperty("username")
	public String getUsername() {
		return username;
	}

	@JsonProperty("username")
	public void setUsername(String username) {
		this.username = username;
	}

	@JsonProperty("firstname")
	public String getFirstname() {
		return firstname;
	}

	@JsonProperty("firstname")
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	@JsonProperty("lastname")
	public String getLastname() {
		return lastname;
	}

	@JsonProperty("lastname")
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@JsonProperty("email")
	public String getEmail() {
		return email;
	}

	@JsonProperty("email")
	public void setEmail(String email) {
		this.email = email;
	}

	@JsonProperty("image")
	public String getImage() {
		return image;
	}

	@JsonProperty("image")
	public void setImage(String image) {
		this.image = image;
	}

	@JsonProperty("ip")
	public String getIp() {
		return ip;
	}

	@JsonProperty("ip")
	public void setIp(String ip) {
		this.ip = ip;
	}

	@JsonProperty("status")
	public String getStatus() {
		return status;
	}

	@JsonProperty("status")
	public void setStatus(String status) {
		this.status = status;
	}

	@JsonProperty("date_added")
	public String getDateAdded() {
		return dateAdded;
	}

	@JsonProperty("date_added")
	public void setDateAdded(String dateAdded) {
		this.dateAdded = dateAdded;
	}

	@JsonProperty("user_group")
	public String getUserGroup() {
		return userGroup;
	}

	@JsonProperty("user_group")
	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
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
