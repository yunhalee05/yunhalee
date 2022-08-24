package com.yunhalee.flo.layout.domain;

import com.yunhalee.flo.product.domain.Product;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class Products {

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    public Products(Product... products) {
        this.products = new ArrayList<>(Arrays.asList(products));
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }
}
