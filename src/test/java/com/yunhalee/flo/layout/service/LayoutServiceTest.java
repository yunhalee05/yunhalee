package com.yunhalee.flo.layout.service;

import static com.yunhalee.flo.product.domain.ProductTest.FIRST_PRODUCT;
import static com.yunhalee.flo.product.domain.ProductTest.SECOND_PRODUCT;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.yunhalee.flo.ServiceTest;
import com.yunhalee.flo.layout.domain.Layout;
import com.yunhalee.flo.layout.domain.Products;
import com.yunhalee.flo.layout.dto.LayoutRequest;
import com.yunhalee.flo.layout.dto.LayoutResponse;
import com.yunhalee.flo.product.domain.Product;
import com.yunhalee.flo.product.dto.ProductResponse;
import com.yunhalee.flo.product.dto.ProductResponses;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.assertj.core.api.Assertions.assertThat;

class LayoutServiceTest extends ServiceTest {

    private static final String NAME = "testLayout";

    private Layout layout;

    @InjectMocks
    private LayoutService layoutService = new LayoutService(layoutRepository, productService);

    @BeforeEach
    public void set_up() {
        layout = Layout.builder()
            .name(NAME)
            .products(new ArrayList<>())
            .build();
    }

    @Test
    public void create_product() {
        // given
        LayoutRequest request = new LayoutRequest(NAME,
            Arrays.asList(FIRST_PRODUCT.getId(), SECOND_PRODUCT.getId()));

        // when
        when(productService.findById(FIRST_PRODUCT.getId())).thenReturn(FIRST_PRODUCT);
        when(productService.findById(SECOND_PRODUCT.getId())).thenReturn(FIRST_PRODUCT);
        when(layoutRepository.save(any())).thenReturn(layout);
        LayoutResponse response = layoutService.createLayout(request);

        // then
        check_layout_equals(layout, response);
    }

    private void check_layout_equals(Layout layout, LayoutResponse response) {
        assertThat(layout.getId()).isEqualTo(response.getId());
        assertThat(layout.getName()).isEqualTo(response.getName());
        check_products_equals(layout.getProducts(), response.getProducts());
    }

    private void check_products_equals(List<Product> products, ProductResponses responses) {
        assertThat(products.size()).isEqualTo(responses.getProducts().size());
        IntStream.range(0, products.size())
            .forEach(index -> check_product_equals(products.get(index), responses.getProducts().get(index)));
    }

    private void check_product_equals(Product product, ProductResponse response) {
        assertThat(response.getId()).isEqualTo(product.getId());
        assertThat(response.getName()).isEqualTo(product.getName());
        assertThat(response.getPrice()).isEqualTo(product.getPrice());
    }



}