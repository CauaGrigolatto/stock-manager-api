package br.com.codebeans.stockapi.model.dto;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import br.com.codebeans.stockapi.model.mapper.BasicMapper;
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

    public static <DTO, E> PaginationResponseDTO<List<DTO>> buildResponse(Page<E> page, BasicMapper<DTO, E> itemsMapper) {
        Pageable pageable = page.getPageable();
        List<E> items = page.getContent();
        List<DTO> itemsDTO = itemsMapper.toListDTO(items);

        PaginationResponseDTO<List<DTO>> response = new PaginationResponseDTO<List<DTO>>();
        response.setStatus(HttpStatus.OK.value());
        response.setData(itemsDTO);
        response.setMessage(null);
        response.setPage(pageable.isUnpaged() ? 0 : pageable.getPageNumber());
        response.setPageSize(pageable.isUnpaged() ? items.size() :pageable.getPageSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        
        return response;
    }
}
