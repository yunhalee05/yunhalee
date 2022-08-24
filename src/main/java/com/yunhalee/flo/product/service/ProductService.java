package com.yunhalee.flo.product.service;

import com.yunhalee.flo.product.domain.Product;
import com.yunhalee.flo.product.domain.ProductRepository;
import com.yunhalee.flo.product.dto.ProductRequest;
import com.yunhalee.flo.product.dto.ProductResponse;
import com.yunhalee.flo.product.exception.ProductNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
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

    public ProductResponse findProductById(String id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException("Cannot find product with id : " + id));
        return new ProductResponse(product);
    }


    public List<ProductResponse> findProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
            .map(product -> new ProductResponse(product))
            .collect(Collectors.toList());
    }
}
