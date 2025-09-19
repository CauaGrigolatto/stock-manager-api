package br.com.codebeans.stockapi.model.dto;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SaveItemDTO(
    @NotBlank
    @Length(max=255)
    String name,

    @Min(value=0)
    Integer quantity,

    @DecimalMin(value="0.01", inclusive=true)
    @Digits(integer=5, fraction=2)
    BigDecimal price,

    @NotNull
    Integer categoryId,

    @NotBlank
    @Length(max=255)
    String description
) {
    
}
