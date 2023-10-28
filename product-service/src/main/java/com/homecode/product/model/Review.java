package com.homecode.product.model;

import com.homecode.commons.module.domain.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "review")
public class Review extends AbstractEntity {

    @NotNull
    @Column(name = "title",nullable = false)
    private String title;
    @NotNull
    @Column(name = "description",nullable = false)
    private String description;
    @NotNull
    @Column(name = "rating",nullable = false)
    private Long rating;
}
