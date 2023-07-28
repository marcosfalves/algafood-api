package com.algaworks.algafood.api;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@UtilityClass
public class ResourceUriHelper {

	public static void addUriInResponseHeader(Object resourceId) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
			.path("/{id}")
			.buildAndExpand(resourceId).toUri();

		var requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes != null) {
			var response = ((ServletRequestAttributes) requestAttributes).getResponse();
			if (response != null) {
				response.setHeader(HttpHeaders.LOCATION, uri.toString());
			}
		}
	}
	
}
