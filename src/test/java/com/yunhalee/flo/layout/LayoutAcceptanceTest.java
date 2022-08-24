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

    private static final String LAYOUT_API = "/layouts";
    private static final String NAME = "testLayout";
    private static final String UPDATE_NAME = "updatedTestLayout";
    private static final String FIRST_PRODUCT_NAME = "firstProduct";
    private static final int FIRST_PRODUCT_PRICE = 30;
    private static final String SECOND_PRODUCT_NAME = "secondProduct";
    private static final int SECOND_PRODUCT_PRICE = 20;
    private static final String THIRD_PRODUCT_NAME = "thirdProduct";
    private static final int THIRD_PRODUCT_PRICE = 10;

    @Test
    void create_layout() {
        // given
        String firstProductId = create_product(FIRST_PRODUCT_NAME, FIRST_PRODUCT_PRICE);
        String secondProductId = create_product(SECOND_PRODUCT_NAME, SECOND_PRODUCT_PRICE);

        // when
        ExtractableResponse<Response> createLayoutResponse = create_layout_request(NAME, Arrays.asList(firstProductId, secondProductId));
        // then
        check_layout_created(createLayoutResponse);
    }

    @Test
    void create_layout_with_already_existing_name_is_invalid() {
        // given
        String firstProductId = create_product(FIRST_PRODUCT_NAME, FIRST_PRODUCT_PRICE);
        ExtractableResponse<Response> createFirstLayoutResponse = create_layout_request(NAME, Arrays.asList(firstProductId));
        check_layout_created(createFirstLayoutResponse);
        String secondProductId = create_product(SECOND_PRODUCT_NAME, SECOND_PRODUCT_PRICE);

        // when
        ExtractableResponse<Response> createLayoutResponse = create_layout_request(NAME, Arrays.asList(firstProductId, secondProductId));
        // then
        check_layout_not_created(createLayoutResponse);
    }

    @Test
    void find_layout() {
        // given
        String productId = create_product(FIRST_PRODUCT_NAME, FIRST_PRODUCT_PRICE);
        ExtractableResponse<Response> createLayoutResponse = create_layout_request(NAME, Arrays.asList(productId));
        check_layout_created(createLayoutResponse);
        String id = get_id_from_response(createLayoutResponse);

        // when
        ExtractableResponse<Response> findLayoutResponse = find_layout_request(id);
        // then
        check_layout_found(findLayoutResponse);
    }

    @Test
    void find_layout_with_not_existing_id_is_invalid() {
        // given
        String id = "invalidId";

        // when
        ExtractableResponse<Response> findLayoutResponse = find_layout_request(id);
        // then
        check_layout_not_found(findLayoutResponse);
    }


    @Test
    void find_layouts() {
        // given
        String firstProductId = create_product(FIRST_PRODUCT_NAME, FIRST_PRODUCT_PRICE);
        ExtractableResponse<Response> createFirstLayoutResponse = create_layout_request(NAME, Arrays.asList(firstProductId));
        check_layout_created(createFirstLayoutResponse);
        String secondProductId = create_product(SECOND_PRODUCT_NAME, SECOND_PRODUCT_PRICE);
        ExtractableResponse<Response> createSecondLayoutResponse = create_layout_request(UPDATE_NAME, Arrays.asList(secondProductId));
        check_layout_created(createSecondLayoutResponse);

        // when
        ExtractableResponse<Response> findLayoutsResponse = find_layouts_request();
        // then
        check_layouts_found(findLayoutsResponse);
    }

    @Test
    void update_layout() {
        // given
        String firstProductId = create_product(FIRST_PRODUCT_NAME, FIRST_PRODUCT_PRICE);
        ExtractableResponse<Response> createLayoutResponse = create_layout_request(NAME, Arrays.asList(firstProductId));
        check_layout_created(createLayoutResponse);
        String id = get_id_from_response(createLayoutResponse);
        String secondProductId = create_product(SECOND_PRODUCT_NAME, SECOND_PRODUCT_PRICE);

        // when
        ExtractableResponse<Response> updateLayoutResponse = update_layout_request(id, UPDATE_NAME, Arrays.asList(secondProductId));
        // then
        check_layout_updated(updateLayoutResponse);
    }

    @Test
    void update_layout_with_already_existing_name_is_invalid() {
        // given
        String firstProductId = create_product(FIRST_PRODUCT_NAME, FIRST_PRODUCT_PRICE);
        ExtractableResponse<Response> createFirstLayoutResponse = create_layout_request(NAME, Arrays.asList(firstProductId));
        check_layout_created(createFirstLayoutResponse);
        String secondProductId = create_product(SECOND_PRODUCT_NAME, SECOND_PRODUCT_PRICE);
        ExtractableResponse<Response> createSecondLayoutResponse = create_layout_request(UPDATE_NAME, Arrays.asList(secondProductId));
        check_layout_created(createSecondLayoutResponse);
        String thirdProductId = create_product(THIRD_PRODUCT_NAME, THIRD_PRODUCT_PRICE);
        String id = get_id_from_response(createFirstLayoutResponse);

        // when
        ExtractableResponse<Response> updateLayoutResponse = update_layout_request(id, UPDATE_NAME, Arrays.asList(thirdProductId));
        // then
        check_layout_not_updated(updateLayoutResponse);
    }


    @Test
    void delete_layout() {
        // given
        String productId = create_product(FIRST_PRODUCT_NAME, FIRST_PRODUCT_PRICE);
        ExtractableResponse<Response> createLayoutResponse = create_layout_request(NAME, Arrays.asList(productId));
        check_layout_created(createLayoutResponse);
        String id = get_id_from_response(createLayoutResponse);

        // when
        ExtractableResponse<Response> deleteLayoutResponse = delete_layout_request(id);
        // then
        check_layout_deleted(deleteLayoutResponse);
    }

    private ExtractableResponse<Response> create_layout_request(String name,
        List<String> productIds) {
        return create_request(new LayoutRequest(name, productIds), LAYOUT_API);
    }

    private void check_layout_created(ExtractableResponse<Response> response) {
        check_create_response(response);
    }

    private void check_layout_not_created(ExtractableResponse<Response> response) {
        check_bad_request_response(response);
    }

    private ExtractableResponse<Response> find_layout_request(String id) {
        return find_request(LAYOUT_API + SLASH + id);
    }

    private void check_layout_found(ExtractableResponse<Response> response) {
        check_ok_response(response);
    }

    private void check_layout_not_found(ExtractableResponse<Response> response) {
        check_not_found_response(response);
    }


    private ExtractableResponse<Response> find_layouts_request() {
        return find_request(LAYOUT_API);
    }

    private void check_layouts_found(ExtractableResponse<Response> response) {
        check_ok_response(response);
    }

    private ExtractableResponse<Response> update_layout_request(String id, String name,
        List<String> productIds) {
        return update_request(new LayoutRequest(name, productIds), LAYOUT_API + SLASH + id);
    }

    private void check_layout_updated(ExtractableResponse<Response> response) {
        check_ok_response(response);
    }

    private void check_layout_not_updated(ExtractableResponse<Response> response) {
        check_bad_request_response(response);
    }

    private ExtractableResponse<Response> delete_layout_request(String id) {
        return delete_request(LAYOUT_API + SLASH + id);
    }

    private void check_layout_deleted(ExtractableResponse<Response> response) {
        check_delete_response(response);
    }

    private String create_product(String name, int price) {
        ExtractableResponse<Response> response = create_product_request(name, price);
        check_product_created(response);
        return get_id_from_response(response);
    }


}
