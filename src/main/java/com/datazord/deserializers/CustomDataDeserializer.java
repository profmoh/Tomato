package com.datazord.deserializers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.datazord.json.tomato.pojo.categories.Category;
import com.datazord.json.tomato.pojo.categories.Data;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class CustomDataDeserializer extends StdDeserializer<Data>/* implements ResolvableDeserializer*/ {

	private static final long serialVersionUID = 5202866125350513314L;

	public CustomDataDeserializer() {
		super(Data.class);
	}

	@Override
	public Data deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
		Data data = new Data();

		if (jsonParser.getCodec() == null)
			return data;

		JsonNode node = jsonParser.getCodec().readTree(jsonParser);
		List<Category> categories = new ArrayList<>();

		node.forEach(n -> {
			n.forEach(c -> {
				Category category = new Category();

				if (c.get("category_id") != null) category.setCategoryId(c.get("category_id").asLong());
				if (c.get("parent_id") != null) category.setParentId(c.get("parent_id").asLong());
				if (c.get("name") != null) category.setName(c.get("name").textValue());
				if (c.get("description") != null) category.setDescription(c.get("description").textValue());
				if (c.get("sort_order") != null) category.setSortOrder(c.get("sort_order").textValue());
				if (c.get("meta_title") != null) category.setMetaTitle(c.get("meta_title").textValue());
				if (c.get("meta_description") != null) category.setMetaDescription(c.get("meta_description").textValue());
				if (c.get("meta_keyword") != null) category.setMetaKeyword(c.get("meta_keyword").textValue());
				if (c.get("language_id") != null) category.setLanguageId(c.get("language_id").asInt());
				if (c.get("image") != null) category.setImage(c.get("image").textValue());
				if (c.get("original_image") != null) category.setOriginalImage(c.get("original_image").textValue());

				Data dat = null;
				ObjectMapper mapper = new ObjectMapper();

				try {
					dat = mapper.readValue(mapper.treeAsTokens(c.get("categories")), Data.class);
				} catch (IOException e) {
					e.printStackTrace();
				}

				category.setCategories(dat);
				categories.add(category);

				System.out.println(c.toString());
			});
		});

		categories.forEach(c -> {
			if (!data.getCategoriesMap().containsKey(c.getCategoryId()))
				data.getCategoriesMap().put(c.getCategoryId(), new ArrayList<>());

			data.getCategoriesMap().get(c.getCategoryId()).add(c);
		});

		return data;
	}

}