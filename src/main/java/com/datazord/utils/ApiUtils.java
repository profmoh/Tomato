package com.datazord.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class ApiUtils {
	public static <T> ResponseEntity<T> doRequest(String headerName, String authorization, String bodyJson,
			MultiValueMap<String, String> headerMap, String url, HttpMethod httpMethod, Class<T> responseClass)
			throws RestClientException {
		HttpHeaders headers = new HttpHeaders();
		RestTemplate restTemplate = new RestTemplate();

		headers.add(headerName, authorization);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");

		if (!Utils.isEmptyMap(headerMap))
			headers.addAll(headerMap);

		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();

		messageConverters.add(new FormHttpMessageConverter());
		messageConverters.add(new StringHttpMessageConverter());
		messageConverters.add(new MappingJackson2HttpMessageConverter());

		restTemplate.setMessageConverters(messageConverters);

		if (StringUtils.isBlank(bodyJson))
			bodyJson = "parameters";

		HttpEntity<String> entity = new HttpEntity<>(bodyJson, headers);

		return restTemplate.exchange(url, httpMethod, entity, responseClass);
	}
}
