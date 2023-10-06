package com.homecode.commons.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    private Long id;
    @NotNull
    @Size(max = 50)
    private String name;
    @NotNull
    @Size(max = 300)
    private String description;

    private Set<ProductDTO> products = new HashSet<>();
}
