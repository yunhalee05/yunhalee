package com.yunhalee.flo.layout.service;

import static com.yunhalee.flo.product.domain.ProductTest.FIRST_PRODUCT;
import static com.yunhalee.flo.product.domain.ProductTest.SECOND_PRODUCT;

import static com.yunhalee.flo.product.domain.ProductTest.THIRD_PRODUCT;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.yunhalee.flo.ServiceTest;
import com.yunhalee.flo.layout.domain.Layout;
import com.yunhalee.flo.layout.dto.LayoutRequest;
import com.yunhalee.flo.layout.dto.LayoutResponse;
import com.yunhalee.flo.layout.dto.LayoutResponses;
import com.yunhalee.flo.layout.exception.LayoutNameAlreadyExistsException;
import com.yunhalee.flo.layout.exception.LayoutNotFoundException;
import com.yunhalee.flo.product.domain.Product;
import com.yunhalee.flo.product.dto.ProductResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.assertj.core.api.Assertions.assertThat;

class LayoutServiceTest extends ServiceTest {

    private static final String ID = "1";
    private static final String NAME = "testLayout";
    private static final String UPDATE_NAME = "updateTestLayout";
    private static final String LAYOUT_NAME_DUPLICATED_EXCEPTION = "Layout already exists with name : ";
    private static final String LAYOUT_NOT_FOUND_EXCEPTION = "Layout does not exist with id : ";

    private Layout layout;

    @InjectMocks
    private LayoutService layoutService = new LayoutService(layoutRepository, productRepository);

    @BeforeEach
    public void set_up() {
        layout = Layout.builder()
            .id(ID)
            .name(NAME)
            .products(new ArrayList<>())
            .build();
    }

    @Test
    public void create_layout() {
        // given
        LayoutRequest request = new LayoutRequest(NAME, Arrays.asList(FIRST_PRODUCT.getId(), SECOND_PRODUCT.getId()));

        // when
        when(productService.findById(FIRST_PRODUCT.getId())).thenReturn(FIRST_PRODUCT);
        when(productService.findById(SECOND_PRODUCT.getId())).thenReturn(FIRST_PRODUCT);
        when(layoutRepository.save(any())).thenReturn(layout);
        LayoutResponse response = layoutService.createLayout(request);

        // then
        check_layout_equals(layout, response);
    }

    @Test
    public void create_layout_with_already_existing_name_is_invalid() {
        // given
        LayoutRequest request = new LayoutRequest(NAME, Arrays.asList(FIRST_PRODUCT.getId(), SECOND_PRODUCT.getId()));

        // when
        when(layoutRepository.existsByName(anyString())).thenReturn(true);

        // then
        assertThatThrownBy(() -> layoutService.createLayout(request))
            .isInstanceOf(LayoutNameAlreadyExistsException.class)
            .hasMessageContaining(LAYOUT_NAME_DUPLICATED_EXCEPTION);
    }


    @Test
    public void find_layout() {
        // given
        layout.addProducts(Arrays.asList(FIRST_PRODUCT, SECOND_PRODUCT));

        // when
        when(layoutRepository.findById(anyString())).thenReturn(Optional.of(layout));
        LayoutResponse response = layoutService.findLayout(layout.getId());

        // then
        check_layout_equals(layout, response);
    }

    @Test
    public void find_layout_with_not_existing_id_is_invalid() {
        // when
        when(layoutRepository.findById(anyString())).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> layoutService.findLayout(ID))
            .isInstanceOf(LayoutNotFoundException.class)
            .hasMessageContaining(LAYOUT_NOT_FOUND_EXCEPTION);
    }

    @Test
    public void find_layouts() {
        // given
        Layout secondLayout = Layout.builder()
            .id(ID)
            .name(NAME)
            .products(Arrays.asList(THIRD_PRODUCT))
            .build();
        layout.addProducts(Arrays.asList(FIRST_PRODUCT, SECOND_PRODUCT));

        // when
        when(layoutRepository.findAll()).thenReturn(Arrays.asList(layout, secondLayout));
        LayoutResponses response = layoutService.findLayouts();

        // then
        check_layout_equals(layout, response.getLayouts().get(0));
        check_layout_equals(secondLayout, response.getLayouts().get(1));
    }

    @Test
    public void update_layout() {
        // given
        LayoutRequest request = new LayoutRequest(NAME, Arrays.asList(SECOND_PRODUCT.getId(), THIRD_PRODUCT.getId()));
        layout.addProducts(Arrays.asList(FIRST_PRODUCT, SECOND_PRODUCT));

        // when
        when(productRepository.findAllById(any())).thenReturn(Arrays.asList(SECOND_PRODUCT, THIRD_PRODUCT));
        when(layoutRepository.findById(anyString())).thenReturn(Optional.of(layout));
        LayoutResponse response = layoutService.updateLayout(ID, request);

        // then
        assertThat(layout.getId()).isEqualTo(response.getId());
        assertThat(layout.getName()).isEqualTo(response.getName());
        check_products_equals(Arrays.asList(SECOND_PRODUCT, THIRD_PRODUCT), response.getProducts());
    }

    @Test
    public void update_layout_with_already_existing_name_is_invalid() {
        // given
        LayoutRequest request = new LayoutRequest(UPDATE_NAME, Arrays.asList(SECOND_PRODUCT.getId(), THIRD_PRODUCT.getId()));
        layout.addProducts(Arrays.asList(FIRST_PRODUCT, SECOND_PRODUCT));

        // when
        when(layoutRepository.findById(anyString())).thenReturn(Optional.of(layout));
        when(layoutRepository.existsByName(anyString())).thenReturn(true);

        // then
        assertThatThrownBy(() -> layoutService.updateLayout(ID, request))
            .isInstanceOf(LayoutNameAlreadyExistsException.class)
            .hasMessageContaining(LAYOUT_NAME_DUPLICATED_EXCEPTION);
    }

    @Test
    public void delete_layout() {
        // when
        when(layoutRepository.findById(anyString())).thenReturn(Optional.of(layout));
        layoutService.deleteLayout(ID);

        // then
        verify(layoutRepository).delete(any());
    }

    private void check_layout_equals(Layout layout, LayoutResponse response) {
        assertThat(layout.getId()).isEqualTo(response.getId());
        assertThat(layout.getName()).isEqualTo(response.getName());
        check_products_equals(layout.getProducts(), response.getProducts());
    }

    private void check_products_equals(List<Product> products, List<ProductResponse> responses) {
        assertThat(products.size()).isEqualTo(responses.size());
        IntStream.range(0, products.size())
            .forEach(index -> check_product_equals(products.get(index), responses.get(index)));
    }

    private void check_product_equals(Product product, ProductResponse response) {
        assertThat(response.getId()).isEqualTo(product.getId());
        assertThat(response.getName()).isEqualTo(product.getName());
        assertThat(response.getPrice()).isEqualTo(product.getPrice());
    }


}