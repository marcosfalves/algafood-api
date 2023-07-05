package com.algaworks.algafood.apitest;

import com.algaworks.algafood.utils.DatabaseCleaner;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

public class IntegrationTestBase {

    @Autowired
    protected DatabaseCleaner databaseCleaner;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void startup() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
        RestAssuredMockMvc.mockMvc(mockMvc);

        RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();
    }

}
