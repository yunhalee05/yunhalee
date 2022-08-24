package com.yunhalee.flo.product;

import com.yunhalee.flo.AcceptanceTest;
import com.yunhalee.flo.product.dto.ProductRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class ProductAcceptanceTest extends AcceptanceTest {

    private static final String PRODUCT_API = "/products";
    private static final String NAME = "testProduct";
    private static final String SECOND_NAME = "secondTestProduct";
    private static final int PRICE = 30;
    private static final int SECOND_PRICE = 20;

    @Test
    void create_product() {
        // when
        ExtractableResponse<Response> createProductResponse = create_product_request();
        // then
        check_product_created(createProductResponse);
    }

    @Test
    void find_product() {
        // given
        ExtractableResponse<Response> createProductResponse = create_product_request();
        check_product_created(createProductResponse);
        String id = get_id_from_response(createProductResponse);

        // when
        ExtractableResponse<Response> findProductResponse = find_product_request(id);
        // then
        check_product_found(findProductResponse);
    }

    @Test
    void find_product_with_not_existing_id_is_invalid() {
        // given
        String id = "invalidId";

        // when
        ExtractableResponse<Response> findProductResponse = find_product_request(id);
        // then
        check_product_not_found(findProductResponse);
    }

    @Test
    void find_products() {
        // given
        ExtractableResponse<Response> createFirstProductResponse = create_product_request();
        check_product_created(createFirstProductResponse);
        ExtractableResponse<Response> createSecondProductResponse = create_product_request(SECOND_NAME, SECOND_PRICE);
        check_product_created(createSecondProductResponse);

        // when
        ExtractableResponse<Response> findProductsResponse = find_products_request();
        // then
        check_products_found(findProductsResponse);
    }

    @Test
    void update_product() {
        // given
        ExtractableResponse<Response> createProductResponse = create_product_request();
        check_product_created(createProductResponse);
        String id = get_id_from_response(createProductResponse);

        // when
        ExtractableResponse<Response> updateProductResponse = update_product_request(id, SECOND_NAME, SECOND_PRICE);
        // then
        check_products_updated(updateProductResponse);
    }

    @Test
    void delete_product() {
        // given
        ExtractableResponse<Response> createProductResponse = create_product_request();
        check_product_created(createProductResponse);
        String id = get_id_from_response(createProductResponse);

        // when
        ExtractableResponse<Response> deleteProductResponse = delete_product_request(id);
        // then
        check_products_deleted(deleteProductResponse);
    }


    public static ExtractableResponse<Response> create_product_request() {
        return create_request(new ProductRequest(NAME, PRICE), PRODUCT_API);
    }

    public static ExtractableResponse<Response> create_product_request(String name, int price) {
        return create_request(new ProductRequest(name, price), PRODUCT_API);
    }

    public static ExtractableResponse<Response> find_product_request(String id) {
        return find_request(PRODUCT_API + SLASH + id);
    }


    public static ExtractableResponse<Response> find_products_request() {
        return find_request(PRODUCT_API);
    }

    public static ExtractableResponse<Response> update_product_request(String id, String name,
        int price) {
        return update_request(new ProductRequest(name, price), PRODUCT_API + SLASH + id);
    }

    public static ExtractableResponse<Response> delete_product_request(String id) {
        return delete_request(PRODUCT_API + SLASH + id);
    }

    public static String get_id_from_response(ExtractableResponse<Response> response) {
        return response.body().jsonPath().getString("id");
    }

    public static void check_product_created(ExtractableResponse<Response> response) {
        check_create_response(response);
    }

    private void check_product_found(ExtractableResponse<Response> response) {
        check_ok_response(response);
    }

    private void check_product_not_found(ExtractableResponse<Response> response) {
        check_not_found_response(response);
    }

    private void check_products_found(ExtractableResponse<Response> response) {
        check_ok_response(response);
    }

    private void check_products_updated(ExtractableResponse<Response> response) {
        check_ok_response(response);
    }

    private void check_products_deleted(ExtractableResponse<Response> response) {
        check_delete_response(response);
    }


}

