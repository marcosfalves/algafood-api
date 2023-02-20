package com.algaworks.algafood.core.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.hateoas.PagedModel.PageMetadata;

import java.io.IOException;

//@JsonComponent
public class PageMetadataJsonSerializer extends JsonSerializer<PageMetadata> {
    @Override
    public void serialize(PageMetadata page, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeNumberField("size", page.getSize());
        jsonGenerator.writeNumberField("page", page.getNumber());
        jsonGenerator.writeNumberField("totalElements", page.getTotalElements());
        jsonGenerator.writeNumberField("totalPages", page.getTotalPages());

        jsonGenerator.writeEndObject();
    }
}
