package com.algaworks.algafood.core.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.mediatype.hal.HalConfiguration;
import org.springframework.http.MediaType;

@Configuration
public class ApiHalConfiguration {

	@Bean
	public HalConfiguration globalPolicy() {
		return new HalConfiguration()
				.withMediaType(MediaType.APPLICATION_JSON)
				.withMediaType(ApiMediaTypes.V1_APPLICATION_JSON)
				.withMediaType(ApiMediaTypes.V2_APPLICATION_JSON);
	}
}