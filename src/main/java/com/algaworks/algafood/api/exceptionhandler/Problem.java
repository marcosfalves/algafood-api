package com.algaworks.algafood.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.List;

@JsonInclude(Include.NON_NULL)
@Getter
@ToString
@Builder
public class Problem {

    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime timestamp;

    private String type;

    private String title;

    private String detail;

    private String userMessage;

    private List<Object> objects;

    @Getter
    @ToString
    @Builder
    public static class Object {

        private String name;

        private String userMessage;
    }
}
