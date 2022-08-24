package com.yunhalee.flo.product.domain;

import com.yunhalee.flo.layout.domain.Layout;
import com.yunhalee.flo.product.dto.ProductRequest;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "layout_id")
    private Layout layout;

    @Builder
    public Product(String id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public void update(ProductRequest request) {
        this.name = request.getName();
        this.price = request.getPrice();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
