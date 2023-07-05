package com.algaworks.algafood.utils;

import org.springframework.security.test.context.support.WithMockUser;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public @interface ApiTestSecurity {

    @WithMockUser(
            username = "user@mock.com",
            authorities = {
                    "SCOPE_READ"
            }
    )
    @Retention(RUNTIME)
    @Target(METHOD)
    @interface AuthenticatedRead {
    }

    @WithMockUser(
            username = "user@mock.com",
            authorities = {
                    "SCOPE_WRITE"
            }
    )
    @Retention(RUNTIME)
    @Target(METHOD)
    @interface AuthenticatedWrite {
    }
}
