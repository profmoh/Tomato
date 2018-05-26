package com.datazord.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor(onConstructor = @__({ @JsonCreator }))
@ToString
public class Username implements Serializable {

	private static final long serialVersionUID = -4699879510889464914L;

	private String username;
}
