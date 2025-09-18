package br.com.codebeans.stockapi.model.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

public record StockItemDTO(
    Integer id,
    String name,
    Integer quantity,
    BigDecimal price,
    ItemCategoryDTO category,
    String description,
    Timestamp createdAt
) {

}
