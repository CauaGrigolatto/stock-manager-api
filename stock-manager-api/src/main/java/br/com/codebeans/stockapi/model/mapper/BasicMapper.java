package br.com.codebeans.stockapi.model.mapper;

import java.util.List;

public interface BasicMapper<DTO, E> {
    DTO toDTO(E entity);
    List<DTO> toListDTO(List<E> entities);
}
