package br.com.codebeans.stockapi.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.codebeans.stockapi.model.dto.ItemCategoryDTO;
import br.com.codebeans.stockapi.model.dto.SaveCategoryDTO;
import br.com.codebeans.stockapi.model.entity.ItemCategory;

@Mapper(componentModel="spring")
public interface CategoryMapper extends BasicMapper<ItemCategoryDTO, ItemCategory> {

    @Mapping(target="id", ignore=true)
    ItemCategory toItemCategory(SaveCategoryDTO request);
}
