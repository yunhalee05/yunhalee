package com.yunhalee.flo.product.controller;

import com.yunhalee.flo.product.dto.ProductRequest;
import com.yunhalee.flo.product.dto.ProductResponse;
import com.yunhalee.flo.product.dto.ProductResponses;
import com.yunhalee.flo.product.service.ProductService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest request) {
        ProductResponse product = productService.createProduct(request);
        return ResponseEntity.created(URI.create("/products/" + product.getId())).body(product);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findProduct(@PathVariable("id") String id) {
        return ResponseEntity.ok(productService.findProductById(id));
    }

    @GetMapping()
    public ResponseEntity<ProductResponses> findProducts() {
        return ResponseEntity.ok(productService.findProducts());
    }

}
