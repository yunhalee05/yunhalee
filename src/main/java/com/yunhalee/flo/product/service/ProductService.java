package com.yunhalee.flo.product.service;

import com.yunhalee.flo.product.domain.Product;
import com.yunhalee.flo.product.domain.ProductRepository;
import com.yunhalee.flo.product.dto.ProductRequest;
import com.yunhalee.flo.product.dto.ProductResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse createProduct(ProductRequest request) {
        Product product = productRepository.save(request.toProduct());
        return new ProductResponse(product);
    }
}
