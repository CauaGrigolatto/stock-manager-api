package br.com.codebeans.stockapi.model.dto;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record SaveItemDTO(
    @NotBlank(message="O nome do produto não pode estar vazio.")
    @Length(max=255, message="O tamanho máximo do nome do produto não pode exceder {max} caracteres.")
    String name,

    @Min(value=0, message="A quantidade mínima deve ser maior ou igual a {value}.")
    Integer quantity,

    @DecimalMin(value="0.01", inclusive=true, message="O preço mínimo do produto deve ser {value}")
    @Digits(integer=5, fraction=2)
    BigDecimal price,

    Integer categoryId,

    @NotBlank(message="A descrição do produto não pode estar vazia.")
    @Length(max=255, message="O tamanho máximo da descrição do produto não pode exceder {max} caracteres.")
    String description
) {
    
}
