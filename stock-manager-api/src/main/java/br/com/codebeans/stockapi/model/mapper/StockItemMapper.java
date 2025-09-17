package br.com.codebeans.stockapi.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.codebeans.stockapi.model.dto.SaveItemRequest;
import br.com.codebeans.stockapi.model.dto.StockItemDTO;
import br.com.codebeans.stockapi.model.entity.StockItem;

@Mapper(componentModel="spring")
public interface StockItemMapper {
    @Mapping(target="id", ignore=true)
    @Mapping(source="categoryId", target="category.id")
    StockItem toStockItem(SaveItemRequest SaveItemRequest);

    StockItemDTO toDTO(StockItem stockItem);
}
