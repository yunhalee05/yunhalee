package com.yunhalee.flo.layout.service;

import com.yunhalee.flo.layout.domain.Layout;
import com.yunhalee.flo.layout.domain.LayoutRepository;
import com.yunhalee.flo.layout.dto.LayoutRequest;
import com.yunhalee.flo.layout.dto.LayoutResponse;
import com.yunhalee.flo.layout.dto.LayoutResponses;
import com.yunhalee.flo.layout.exception.LayoutNotFoundException;
import com.yunhalee.flo.product.domain.Product;
import com.yunhalee.flo.product.domain.ProductRepository;
import com.yunhalee.flo.product.dto.ProductResponse;
import com.yunhalee.flo.layout.exception.LayoutNameAlreadyExistsException;
import com.yunhalee.flo.product.service.ProductService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LayoutService {

    private static final String LAYOUT_NOT_FOUND_EXCEPTION = "Layout does not exist with id : ";
    private static final String LAYOUT_NAME_DUPLICATED_EXCEPTION = "Layout already exists with name : ";


    private LayoutRepository layoutRepository;
    private ProductRepository productRepository;



    public LayoutService(LayoutRepository layoutRepository, ProductRepository productRepository) {
        this.layoutRepository = layoutRepository;
        this.productRepository = productRepository;
    }

    public LayoutResponse createLayout(LayoutRequest request) {
        checkLayoutName(request.getName());
        List<Product> products = products(request.getProducts());
        Layout layout = layoutRepository.save(request.toLayout());
        layout.addProducts(products);
        return layoutResponse(layout);
    }

    private void checkLayoutName(String name) {
        if (layoutRepository.existsByName(name)) {
            throw new LayoutNameAlreadyExistsException(LAYOUT_NAME_DUPLICATED_EXCEPTION + name) ;
        }
    }

    @Transactional(readOnly = true)
    public LayoutResponse findLayout(String id) {
        Layout layout = findById(id);
        return layoutResponse(layout);
    }

    @Transactional(readOnly = true)
    public LayoutResponses findLayouts() {
        return LayoutResponses.of(layoutRepository.findAll().stream()
            .map(this::layoutResponse)
            .collect(Collectors.toList()));
    }

    public synchronized LayoutResponse updateLayout(String id, LayoutRequest request) {
        Layout layout = findById(id);
        checkLayoutName(layout.getName(), request.getName());
        layout.update(request.getName(), products(request.getProducts()));
        return layoutResponse(layout);
    }

    private void checkLayoutName(String originalName, String updateName) {
        if (isNameChanged(originalName, updateName) && layoutRepository.existsByName(updateName)) {
            throw new LayoutNameAlreadyExistsException(LAYOUT_NAME_DUPLICATED_EXCEPTION + updateName) ;
        }
    }

    private boolean isNameChanged(String originalName, String updateName) {
        return !originalName.equals(updateName);
    }


    public void deleteLayout(String id) {
        Layout layout = findById(id);
        layout.emptyProducts();
        layoutRepository.delete(layout);
    }


    private Layout findById(String id) {
        return layoutRepository.findById(id)
            .orElseThrow(() -> new LayoutNotFoundException(LAYOUT_NOT_FOUND_EXCEPTION + id));
    }

    private LayoutResponse layoutResponse(Layout layout) {
        return LayoutResponse.of(layout,
            layout.getProducts().stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList())
            );
    }

    private List<Product> products(List<String> products) {
        return productRepository.findAllById(products);
    }
}
