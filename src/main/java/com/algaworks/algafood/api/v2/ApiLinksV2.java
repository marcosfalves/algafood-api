package com.algaworks.algafood.api.v2;

import com.algaworks.algafood.api.v2.controller.CidadeControllerV2;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ApiLinksV2 {

    public Link linkToCidades(String relation) {
        return linkTo(methodOn(CidadeControllerV2.class).listar())
                .withRel(relation);
    }

    public Link linkToCidades() {
        return linkToCidades(IanaLinkRelations.SELF.value());
    }
}
