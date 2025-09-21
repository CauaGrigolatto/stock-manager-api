package br.com.codebeans.stockapi.model.dto;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoriesFilterDTO extends PaginationFilterDTO {
    private String name;

    public boolean hasValidName() {
        return StringUtils.isNotBlank(name);
    }
}
