package com.yunhalee.flo.layout;

import static com.yunhalee.flo.product.ProductAcceptanceTest.check_product_created;
import static com.yunhalee.flo.product.ProductAcceptanceTest.create_product_request;
import static com.yunhalee.flo.product.ProductAcceptanceTest.get_id_from_response;

import com.yunhalee.flo.AcceptanceTest;
import com.yunhalee.flo.layout.dto.LayoutRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

public class LayoutAcceptanceTest extends AcceptanceTest {

    private static final String NAME = "testLayout";
    private static final String FIRST_PRODUCT_NAME = "firstProduct";
    private static final int FIRST_PRODUCT_PRICE = 30;
    private static final String SECOND_PRODUCT_NAME = "secondProduct";
    private static final int SECOND_PRODUCT_PRICE = 20;

    @Test
    void create_layout() {
        // given
        ExtractableResponse<Response> firstProductResponse = create_product_request(FIRST_PRODUCT_NAME, FIRST_PRODUCT_PRICE);
        check_product_created(firstProductResponse);
        ExtractableResponse<Response> secondProductResponse = create_product_request(SECOND_PRODUCT_NAME, SECOND_PRODUCT_PRICE);
        check_product_created(secondProductResponse);

        // when
        ExtractableResponse<Response> createLayoutResponse = create_layout_request(NAME,
            Arrays.asList(get_id_from_response(firstProductResponse),
                get_id_from_response(secondProductResponse)));
        // then
        check_layout_created(createLayoutResponse);
    }

    @Test
    void find_layout() {
        // given
        ExtractableResponse<Response> productResponse = create_product_request(FIRST_PRODUCT_NAME, FIRST_PRODUCT_PRICE);
        check_product_created(productResponse);
        ExtractableResponse<Response> createLayoutResponse = create_layout_request(NAME, Arrays.asList(get_id_from_response(productResponse)));
        check_layout_created(createLayoutResponse);
        String id = get_id_from_response(createLayoutResponse);

        // when
        ExtractableResponse<Response> findLayoutResponse = find_layout_request(id);
        // then
        check_layout_found(findLayoutResponse);
    }

    @Test
    void find_layouts() {
        // given
        ExtractableResponse<Response> firstProductResponse = create_product_request(FIRST_PRODUCT_NAME, FIRST_PRODUCT_PRICE);
        check_product_created(firstProductResponse);
        ExtractableResponse<Response> createFirstLayoutResponse = create_layout_request(NAME, Arrays.asList(get_id_from_response(firstProductResponse)));
        check_layout_created(createFirstLayoutResponse);
        ExtractableResponse<Response> secondProductResponse = create_product_request(SECOND_PRODUCT_NAME, SECOND_PRODUCT_PRICE);
        check_product_created(secondProductResponse);
        ExtractableResponse<Response> createSecondLayoutResponse = create_layout_request(NAME, Arrays.asList(get_id_from_response(secondProductResponse)));
        check_layout_created(createSecondLayoutResponse);

        // when
        ExtractableResponse<Response> findLayoutsResponse = find_layouts_request();
        // then
        check_layouts_found(findLayoutsResponse);
    }

    @Test
    void update_layout() {
        // given
        ExtractableResponse<Response> firstProductResponse = create_product_request(FIRST_PRODUCT_NAME, FIRST_PRODUCT_PRICE);
        check_product_created(firstProductResponse);
        ExtractableResponse<Response> createLayoutResponse = create_layout_request(NAME, Arrays.asList(get_id_from_response(firstProductResponse)));
        check_layout_created(createLayoutResponse);
        String id = get_id_from_response(createLayoutResponse);
        ExtractableResponse<Response> secondProductResponse = create_product_request(SECOND_PRODUCT_NAME, SECOND_PRODUCT_PRICE);
        check_product_created(secondProductResponse);

        // when
        ExtractableResponse<Response> updateLayoutResponse = update_layout_request(id, "updateTestLayout", Arrays.asList(get_id_from_response(secondProductResponse)));
        // then
        check_layout_updated(updateLayoutResponse);
    }

    @Test
    void delete_layout() {
        // given
        ExtractableResponse<Response> firstProductResponse = create_product_request(FIRST_PRODUCT_NAME, FIRST_PRODUCT_PRICE);
        check_product_created(firstProductResponse);
        ExtractableResponse<Response> createLayoutResponse = create_layout_request(NAME, Arrays.asList(get_id_from_response(firstProductResponse)));
        check_layout_created(createLayoutResponse);
        String id = get_id_from_response(createLayoutResponse);

        // when
        ExtractableResponse<Response> deleteLayoutResponse = delete_layout_request(id);
        // then
        check_layout_deleted(deleteLayoutResponse);
    }

    private ExtractableResponse<Response> create_layout_request(String name, List<String> productIds) {
        return create_request(new LayoutRequest(name, productIds), "/layouts");
    }

    private void check_layout_created(ExtractableResponse<Response> response) {
        check_create_response(response);
    }

    private ExtractableResponse<Response> find_layout_request(String id) {
        return find_request("/layouts/" + id);
    }

    private void check_layout_found(ExtractableResponse<Response> response) {
        check_ok_response(response);
    }

    private ExtractableResponse<Response> find_layouts_request() {
        return find_request("/layouts");
    }

    private void check_layouts_found(ExtractableResponse<Response> response) {
        check_ok_response(response);
    }

    private ExtractableResponse<Response> update_layout_request(String id, String name, List<String> productIds) {
        return update_request(new LayoutRequest(name, productIds), "/layouts/" + id);
    }

    private void check_layout_updated(ExtractableResponse<Response> response) {
        check_ok_response(response);
    }

    private ExtractableResponse<Response> delete_layout_request(String id) {
        return delete_request("/layouts/" + id);
    }

    private void check_layout_deleted(ExtractableResponse<Response> response) {
        check_delete_response(response);
    }



}
