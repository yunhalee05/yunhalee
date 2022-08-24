package com.yunhalee.flo.layout.domain;

import com.yunhalee.flo.product.domain.Product;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class Products {

    @OneToMany(mappedBy = "layout")
    private List<Product> products = new ArrayList<>();

    public Products(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public void addProducts(List<Product> products) {
        products.stream().forEach(this::addProduct);
    }

    private void addProduct(Product product) {
        this.products.add(product);
    }

}
