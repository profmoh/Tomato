package com.datazord.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Document(collection = "users")
public class User extends BaseModel implements Serializable {

	private static final long serialVersionUID = 7585256860194211001L;

	private String username;

	@JsonIgnore
	private String password;

	@Email
	private String email;

	@Builder.Default()
	private boolean active = true;

	@Builder.Default()
	private List<GrantedAuthority> authorities = new ArrayList<>();

}
