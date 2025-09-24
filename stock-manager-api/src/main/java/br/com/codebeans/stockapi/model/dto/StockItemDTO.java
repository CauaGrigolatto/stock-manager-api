package br.com.codebeans.stockapi.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public record StockItemDTO(
    Integer id,
    String name,
    Integer quantity,
    BigDecimal price,
    ItemCategoryDTO category,
    String description,
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", shape=Shape.STRING)
    LocalDateTime createdAt,
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", shape=Shape.STRING)
    LocalDateTime updatedAt
) {

}
