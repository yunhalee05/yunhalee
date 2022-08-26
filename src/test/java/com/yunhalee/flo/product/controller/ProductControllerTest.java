package com.yunhalee.flo.product.controller;

import static com.yunhalee.flo.product.domain.ProductTest.FIRST_PRODUCT;
import static com.yunhalee.flo.product.domain.ProductTest.SECOND_PRODUCT;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.yunhalee.flo.ApiTest;
import com.yunhalee.flo.product.dto.ProductRequest;
import com.yunhalee.flo.product.dto.ProductResponse;
import com.yunhalee.flo.product.dto.ProductResponses;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;

public class ProductControllerTest extends ApiTest {

    private static final String PRODUCT_API = "/products";
    private static final ProductRequest PRODUCT_REQUEST = new ProductRequest(FIRST_PRODUCT.getName(), FIRST_PRODUCT.getPrice());
    public static final ProductResponse FIRST_PRODUCT_RESPONSE = ProductResponse.of(FIRST_PRODUCT);
    public static final ProductResponse SECOND_PRODUCT_RESPONSE = ProductResponse.of(SECOND_PRODUCT);
    private static final ProductResponses PRODUCT_RESPONSES = ProductResponses.of(Arrays.asList(FIRST_PRODUCT_RESPONSE, SECOND_PRODUCT_RESPONSE));

    @Test
    void create_product() throws Exception {
        when(productService.createProduct(any())).thenReturn(FIRST_PRODUCT_RESPONSE);
        this.mockMvc.perform(post(PRODUCT_API)
            .contentType(MediaTypes.HAL_JSON)
            .characterEncoding(UTF_8)
            .content(request(PRODUCT_REQUEST))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andDo(document("product-create",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                productResponseFields()));
    }

    @Test
    void find_product() throws Exception {
        when(productService.findProductById(any())).thenReturn(FIRST_PRODUCT_RESPONSE);
        this.mockMvc.perform(get(PRODUCT_API + SLASH + FIRST_PRODUCT_RESPONSE.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(document("product-find-by-id",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                productResponseFields()));
    }

    @Test
    void find_products() throws Exception {
        when(productService.findProducts()).thenReturn(PRODUCT_RESPONSES);
        this.mockMvc.perform(get(PRODUCT_API)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(document("product-find-all",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                productResponsesFields()));
    }

    @Test
    void update_layout() throws Exception {
        when(productService.updateProduct(anyString(), any())).thenReturn(FIRST_PRODUCT_RESPONSE);
        this.mockMvc.perform(put(PRODUCT_API + SLASH + FIRST_PRODUCT_RESPONSE.getId())
            .contentType(MediaTypes.HAL_JSON)
            .characterEncoding(UTF_8)
            .content(request(PRODUCT_REQUEST))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(document("product-update",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                productResponseFields()));
    }

    @Test
    void delete_layout() throws Exception {
        this.mockMvc.perform(delete(PRODUCT_API + SLASH + FIRST_PRODUCT_RESPONSE.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent())
            .andDo(document("product-delete"));
    }

    public static ResponseFieldsSnippet productResponseFields() {
        return responseFields(
            fieldWithPath("id").description("product id"),
            fieldWithPath("name").description("product name"),
            fieldWithPath("price").description("product price"));
    }

    public static ResponseFieldsSnippet productResponsesFields() {
        return responseFields(
            fieldWithPath("products").description("all the products"),
            fieldWithPath("products.[].id").description("product id"),
            fieldWithPath("products.[].name").description("product name"),
            fieldWithPath("products.[].price").description("product price"));
    }

}