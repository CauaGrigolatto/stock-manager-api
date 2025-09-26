package br.com.codebeans.stockapi.model.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;

public record SaveCategoryDTO(
    @NotBlank(message="O nome da categoria não pode estar vazio.")
    @Length(max=100, message="O tamanho máximo do nome da categoria não pode exceder {max} caracteres.")
    String name
) {

}
