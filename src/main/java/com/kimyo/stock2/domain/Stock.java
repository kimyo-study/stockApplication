package com.kimyo.stock2.domain;

import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class Stock {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Long productId;

    @Getter
    private Long quantity;

    @Version
    private Long version;

    @Builder
    public Stock(Long id, Long productId, Long quantity) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
    }

    public void decrease(Long quantity) {
        if (this.quantity - quantity < 0) {
            throw new RuntimeException("foo");
        }
        this.quantity = this.quantity - quantity;
    }
}
