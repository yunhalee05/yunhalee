package com.yunhalee.flo.product;

import com.yunhalee.flo.AcceptanceTest;
import com.yunhalee.flo.product.dto.ProductRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class ProductAcceptanceTest extends AcceptanceTest {

    private static final String NAME = "testProduct";
    private static final int PRICE = 30;

    @Test
    void create_product() {
        // when
        ExtractableResponse<Response> createProductResponse = create_product_request();
        // then
        check_product_created(createProductResponse);
    }

    @Test
    void create_product_with_already_existing_name_is_invalid() {
        // given
        ExtractableResponse<Response> createProductResponse = create_product_request();
        check_product_created(createProductResponse);

        // when
        ExtractableResponse<Response> createDuplicatedProductResponse = create_product_request();
        // then
        check_product_not_created(createDuplicatedProductResponse);
    }

    @Test
    void find_product() {
        // given
        ExtractableResponse<Response> createProductResponse = create_product_request();
        check_product_created(createProductResponse);

        // when
        ExtractableResponse<Response> findProductResponse = find_product_request(createProductResponse);
        // then
        check_product_found(findProductResponse);
    }

    @Test
    void find_products() {
        // given
        ExtractableResponse<Response> createFirstProductResponse = create_product_request();
        check_product_created(createFirstProductResponse);
        ExtractableResponse<Response> createSecondProductResponse = create_product_request("secondTestProduct", 20);
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

        // when
        ExtractableResponse<Response> updateProductResponse = update_product_request(createProductResponse, "updateTestProduct", 40);
        // then
        check_products_updated(updateProductResponse);
    }

    @Test
    void delete_product() {
        // given
        ExtractableResponse<Response> createProductResponse = create_product_request();
        check_product_created(createProductResponse);

        // when
        ExtractableResponse<Response> deleteProductResponse = delete_product_request(createProductResponse);
        // then
        check_products_deleted(deleteProductResponse);
    }


    public static ExtractableResponse<Response> create_product_request() {
        return create_request(new ProductRequest(NAME, PRICE), "/products");
    }

    public static ExtractableResponse<Response> create_product_request(String name, int price) {
        return create_request(new ProductRequest(name, price), "/products");
    }

    public static ExtractableResponse<Response> find_product_request(
        ExtractableResponse<Response> response) {
        String id = response.body().jsonPath().getString("id");
        return find_request("/products" + id);
    }


    public static ExtractableResponse<Response> find_products_request() {
        return find_request("/products");
    }

    public static ExtractableResponse<Response> update_product_request(
        ExtractableResponse<Response> response, String name, int price) {
        String id = response.body().jsonPath().getString("id");
        return update_request(new ProductRequest(name, price), "/products" + id);
    }

    public static ExtractableResponse<Response> delete_product_request(
        ExtractableResponse<Response> response) {
        String id = response.body().jsonPath().getString("id");
        return delete_request("/products" + id);
    }

    private void check_product_created(ExtractableResponse<Response> response) {
        check_create_response(response);
    }

    private void check_product_found(ExtractableResponse<Response> response) {
        check_ok_response(response);
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

    private void check_product_not_created(ExtractableResponse<Response> response) {
        check_bad_request_response(response);
    }
}

