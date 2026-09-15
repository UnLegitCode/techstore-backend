package ru.unlegit.techstore.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Entity
@Table(name = "products")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product extends BaseEntity {

    @Column(nullable = false)
    String title;
    @JoinColumn(name = "category_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    ProductCategory category;
    @Column(nullable = false)
    double price;
    @Column(name = "previous_price")
    Double previousPrice;
    @Column(nullable = false)
    Double rating;
    @Column(nullable = false)
    int reviews;
    @Column(name = "emoji", nullable = false)
    String emoji;
    @Column
    String badge;
}