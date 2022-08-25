package com.yunhalee.flo.product.service;

import com.yunhalee.flo.ServiceTest;
import com.yunhalee.flo.product.domain.Product;
import com.yunhalee.flo.product.dto.ProductRequest;
import com.yunhalee.flo.product.dto.ProductResponse;
import com.yunhalee.flo.product.dto.ProductResponses;
import com.yunhalee.flo.product.exception.ProductNotFoundException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProductServiceTest extends ServiceTest {

    private static final String ID = "testId";
    private static final String NAME = "testProduct";
    private static final String UPDATE_NAME = "updateTestProduct";
    private static final int PRICE = 30;
    private static final int UPDATE_PRICE = 20;
    private static final String PRODUCT_NOT_FOUND_EXCEPTION = "Cannot find product with id : ";

    @InjectMocks
    private ProductService productService = new ProductService(productRepository);

    private Product product;

    @BeforeEach
    public void set_up() {
        product = Product.builder()
            .id(ID)
            .name(NAME)
            .price(PRICE)
            .build();
    }

    @Test
    public void create_product() {
        // given
        ProductRequest request = new ProductRequest(NAME, PRICE);

        // when
        when(productRepository.save(any())).thenReturn(product);
        ProductResponse response = productService.createProduct(request);

        // then
        check_equals(product, response);
    }

    @Test
    public void find_product() {

        // when
        when(productRepository.findById(anyString())).thenReturn(Optional.of(product));
        ProductResponse response = productService.findProductById(ID);

        // then
        check_equals(product, response);
    }


    @Test
    public void find_product_with_not_existing_id_is_invalid() {
        // when
        when(productRepository.findById(anyString())).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> productService.findProductById(ID))
            .isInstanceOf(ProductNotFoundException.class)
            .hasMessageContaining(PRODUCT_NOT_FOUND_EXCEPTION);
    }


    @Test
    public void find_products() {
        // given
        Product secondProduct = Product.builder()
            .id(ID)
            .name(NAME)
            .price(PRICE)
            .build();

        // when
        when(productRepository.findAll()).thenReturn(List.of(product, secondProduct));
        ProductResponses response = productService.findProducts();

        // then
        assertThat(response.getProducts().size()).isEqualTo(2);
    }

    @Test
    public void update_product() {
        // given
        ProductRequest request = new ProductRequest(UPDATE_NAME, UPDATE_PRICE);

        // when
        when(productRepository.findById(anyString())).thenReturn(Optional.of(product));
        ProductResponse response = productService.updateProduct(ID, request);

        // then
        assertThat(response.getName()).isEqualTo(request.getName());
        assertThat(response.getPrice()).isEqualTo(request.getPrice());
    }

    @Test
    public void delete_product() {
        // when
        when(productRepository.findById(anyString())).thenReturn(Optional.of(product));
        productService.deleteProduct(ID);

        // then
        verify(productRepository).delete(any());
    }


    private void check_equals(Product product, ProductResponse response) {
        assertThat(response.getId()).isEqualTo(product.getId());
        assertThat(response.getName()).isEqualTo(product.getName());
        assertThat(response.getPrice()).isEqualTo(product.getPrice());
    }

}