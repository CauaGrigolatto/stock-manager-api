package br.com.codebeans.stockapi.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.codebeans.stockapi.model.dto.SaveCategoryRequest;
import br.com.codebeans.stockapi.model.entity.ItemCategory;

@Mapper(componentModel="spring")
public interface ItemCategoryMapper {
    @Mapping(target="id", ignore=true)
    ItemCategory toItemCategory(SaveCategoryRequest request);
}
