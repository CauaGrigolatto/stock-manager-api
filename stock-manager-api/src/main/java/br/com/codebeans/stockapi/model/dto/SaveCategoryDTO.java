package br.com.codebeans.stockapi.model.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;

public record SaveCategoryDTO(
    @NotBlank
    @Length(max=100)
    String name
) {

}
