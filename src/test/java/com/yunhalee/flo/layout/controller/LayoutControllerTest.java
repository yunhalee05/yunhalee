package com.yunhalee.flo.layout.controller;

import static com.yunhalee.flo.layout.domain.LayoutTest.FIRST_LAYOUT;
import static com.yunhalee.flo.layout.domain.LayoutTest.SECOND_LAYOUT;
import static com.yunhalee.flo.product.controller.ProductControllerTest.PRODUCT_RESPONSES;
import static com.yunhalee.flo.product.domain.ProductTest.FIRST_PRODUCT;
import static com.yunhalee.flo.product.domain.ProductTest.SECOND_PRODUCT;
import static com.yunhalee.flo.product.domain.ProductTest.THIRD_PRODUCT;
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
import com.yunhalee.flo.layout.dto.LayoutRequest;
import com.yunhalee.flo.layout.dto.LayoutResponse;
import com.yunhalee.flo.layout.dto.LayoutResponses;
import com.yunhalee.flo.product.dto.ProductResponse;
import com.yunhalee.flo.product.dto.ProductResponses;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;

public class LayoutControllerTest extends ApiTest {

    private static final String LAYOUT_API = "/layouts";
    private static final LayoutResponse LAYOUT_RESPONSE = LayoutResponse.of(FIRST_LAYOUT, PRODUCT_RESPONSES);
    private static final LayoutRequest LAYOUT_REQUEST = new LayoutRequest(FIRST_LAYOUT.getName(),
        Arrays.asList(FIRST_PRODUCT.getId(), SECOND_PRODUCT.getId()));

    @Test
    void create_layout() throws Exception {
        when(layoutService.createLayout(any())).thenReturn(LAYOUT_RESPONSE);
        this.mockMvc.perform(post(LAYOUT_API)
            .contentType(MediaTypes.HAL_JSON)
            .characterEncoding(UTF_8)
            .content(request(LAYOUT_REQUEST))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andDo(document("layout-create",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                layoutResponseFields()));
    }

    @Test
    void find_layout() throws Exception {
        when(layoutService.findLayout(any())).thenReturn(LAYOUT_RESPONSE);
        this.mockMvc.perform(get(LAYOUT_API + SLASH + LAYOUT_RESPONSE.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(document("layout-find-by-id",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                layoutResponseFields()));
    }

    @Test
    void find_layouts() throws Exception {
        ProductResponses secondProductResponses = ProductResponses.of(Arrays.asList(ProductResponse.of(THIRD_PRODUCT)));
        LayoutResponse secondLayoutResponse = LayoutResponse.of(SECOND_LAYOUT, secondProductResponses);
        LayoutResponses layoutResponses = LayoutResponses.of(Arrays.asList(LAYOUT_RESPONSE, secondLayoutResponse));
        when(layoutService.findLayouts()).thenReturn(layoutResponses);
        this.mockMvc.perform(get(LAYOUT_API)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(document("layout-find-all",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                layoutResponsesFields()));
    }

    @Test
    void update_layout() throws Exception {
        when(layoutService.updateLayout(anyString(), any())).thenReturn(LAYOUT_RESPONSE);
        this.mockMvc.perform(put(LAYOUT_API + SLASH + LAYOUT_RESPONSE.getId())
            .contentType(MediaTypes.HAL_JSON)
            .characterEncoding(UTF_8)
            .content(request(LAYOUT_REQUEST))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(document("layout-update",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                layoutResponseFields()));
    }

    @Test
    void delete_layout() throws Exception {
        this.mockMvc.perform(delete(LAYOUT_API + SLASH + LAYOUT_RESPONSE.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent())
            .andDo(document("layout-delete"));
    }

    public static ResponseFieldsSnippet layoutResponseFields() {
        return responseFields(
            fieldWithPath("id").description("layout id"),
            fieldWithPath("name").description("layout name"),
            fieldWithPath("products").description("layout products"),
            fieldWithPath("products.products.[].id").description("product id"),
            fieldWithPath("products.products.[].name").description("product name"),
            fieldWithPath("products.products.[].price").description("product price"));
    }

    public static ResponseFieldsSnippet layoutResponsesFields() {
        return responseFields(
            fieldWithPath("layouts").description("all the layouts"),
            fieldWithPath("layouts.[].id").description("layout id"),
            fieldWithPath("layouts.[].name").description("layout name"),
            fieldWithPath("layouts.[].products").description("layout products"),
            fieldWithPath("layouts.[].products.products.[].id").description("product id"),
            fieldWithPath("layouts.[].products.products.[].name").description("product name"),
            fieldWithPath("layouts.[].products.products.[].price").description("product price"));
    }
}