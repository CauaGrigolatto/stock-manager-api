package br.com.codebeans.stockapi.model.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;

public record SaveCategoryRequest(
    @NotBlank
    @Length(max=100)
    String name
) {

}
