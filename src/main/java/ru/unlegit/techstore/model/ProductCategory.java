package ru.unlegit.techstore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Entity
@Table(name = "product_categories")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductCategory extends BaseEntity {

    @Column(nullable = false)
    String title;

    @Column(name = "emoji", nullable = false)
    String emoji;
}