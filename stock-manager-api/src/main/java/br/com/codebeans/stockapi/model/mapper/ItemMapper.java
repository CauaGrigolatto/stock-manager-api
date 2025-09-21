package br.com.codebeans.stockapi.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.codebeans.stockapi.model.dto.SaveItemDTO;
import br.com.codebeans.stockapi.model.dto.StockItemDTO;
import br.com.codebeans.stockapi.model.entity.StockItem;

@Mapper(componentModel="spring")
public interface ItemMapper extends BasicMapper<StockItemDTO, StockItem> {
    @Mapping(target="id", ignore=true)
    @Mapping(target="createdAt", ignore=true)
    @Mapping(source="categoryId", target="category.id")
    StockItem toStockItem(SaveItemDTO SaveItemRequest);
}
