package com.yunhalee.flo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yunhalee.flo.layout.service.LayoutService;
import com.yunhalee.flo.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureRestDocs
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@TestPropertySource(locations = "/application-test.properties")
public abstract class ApiTest {

    protected static final String SLASH = "/";
    protected static final String UTF_8 = "utf-8";

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected ProductService productService;

    @MockBean
    protected LayoutService layoutService;

    private ObjectMapper objectMapper = new ObjectMapper();

    protected String request(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
