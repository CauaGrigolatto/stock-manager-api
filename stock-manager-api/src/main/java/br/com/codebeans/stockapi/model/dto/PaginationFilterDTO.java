package br.com.codebeans.stockapi.model.dto;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaginationFilterDTO {

    private static final String ASC = "ASC";
    private static final String DESC = "DESC";

    private Integer page;
    private Integer pageSize;
    private String sortBy;
    private String direction;

    public static Pageable buildPageable(PaginationFilterDTO filter) {
        if (! filter.hasValidPageSize()) {
            filter.setPageSize(10);
        }
        
        if (! filter.hasValidPage()) {
            filter.setPage(0);
        }

        Sort sort = Sort.unsorted();
        
        if (filter.hasValidSortBy()) {
            if (! filter.hasValidDirection()) {
                filter.setDirection(ASC);
            }
            
            Sort.Direction direction = Sort.Direction.fromString(filter.direction);
            sort = Sort.by(direction, filter.getSortBy());
        }
        
        return PageRequest.of(filter.getPage(), filter.getPageSize(), sort);
    }

    public boolean hasValidPageSize() {
        return pageSize != null && pageSize > 0;
    }

    public boolean hasValidPage() {
        return page != null && page > 0;
    }

    public boolean hasValidDirection() {
        return ASC.equals(direction) || DESC.equals(direction);
    }

    public boolean hasValidSortBy() {
        return StringUtils.isNotBlank(sortBy);
    }
}
