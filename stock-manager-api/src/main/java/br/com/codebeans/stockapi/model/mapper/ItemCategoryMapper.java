package br.com.codebeans.stockapi.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.codebeans.stockapi.model.dto.ItemCategoryDTO;
import br.com.codebeans.stockapi.model.dto.SaveCategoryRequest;
import br.com.codebeans.stockapi.model.entity.ItemCategory;

@Mapper(componentModel="spring")
public interface ItemCategoryMapper {

    @Mapping(target="id", ignore=true)
    ItemCategory toItemCategory(SaveCategoryRequest request);

    ItemCategoryDTO toDTO(ItemCategory itemCategory);

    List<ItemCategoryDTO> toListDTO(List<ItemCategory> categories);
}
