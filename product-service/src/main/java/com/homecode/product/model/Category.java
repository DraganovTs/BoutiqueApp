package com.homecode.product.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.homecode.commons.module.domain.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "category")
public class Category extends AbstractEntity {

    @NotNull
    @Column(name = "name",nullable = false)
    private String name;
    @NotNull
    @Column(name = "description",columnDefinition = "TEXT" ,nullable = false)
    private String description;
    @OneToMany
    @JsonIgnore
    private Set<Product> products = new HashSet<>();
}
