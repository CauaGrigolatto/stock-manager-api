package br.com.codebeans.stockapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaginationResponseDTO<T> extends ResponseDTO<T> {
    private Integer pageSize;
    private Integer page;
    private Integer totalPages;
    private Long totalElements;
}
