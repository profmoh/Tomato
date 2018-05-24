package com.datazord.model;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.springframework.data.mongodb.core.mapping.Document;

import com.datazord.enums.Language;

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
@Document(collection = "tomato_categories")
public class TomatoCategories extends BaseModel implements Serializable {

	private static final long serialVersionUID = 235605989750496716L;

	@NotBlank
	private String name;

	@NotBlank
	private Integer category_id;

	private Integer sort_order;

	private String description;

	private String meta_title;

	private String meta_description;

	private String meta_keyword;

	@Builder.Default
	private Language language_id = Language.en;

//    "image": "",
//    "original_image": "",

}
