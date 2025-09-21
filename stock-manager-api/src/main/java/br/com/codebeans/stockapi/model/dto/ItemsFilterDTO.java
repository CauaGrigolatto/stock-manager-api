package br.com.codebeans.stockapi.model.dto;

import java.time.LocalDate;

import io.micrometer.common.util.StringUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ItemsFilterDTO extends PaginationFilterDTO {
    private String name;

    private Integer maxQuantity;
    private Integer minQuantity;

    private LocalDate createdAfter;
    private LocalDate createdBefore;
    private boolean createdToday;

    private Integer categoryId;
    private String description;

    public boolean hasValidName() {
        return StringUtils.isNotBlank(name);
    }

    public boolean hasValidMaxQuantity() {
        return maxQuantity != null && maxQuantity >= 0;
    }

    public boolean hasValidMinQuantity() {
        return minQuantity != null && minQuantity >= 0;
    }

    public boolean hasValidCreatedAfter() {
        return createdAfter != null;
    }

    public boolean hasValidCreatedBefore() {
        return createdBefore != null;
    }

    public boolean hasValidCategoryId() {
        return categoryId != null && categoryId > 0;
    }

    public boolean hasValidDescription() {
        return StringUtils.isNotBlank(description);
    }
}
