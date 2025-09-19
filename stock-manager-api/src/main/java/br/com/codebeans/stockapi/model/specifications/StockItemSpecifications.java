package br.com.codebeans.stockapi.model.specifications;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.Specification;

import br.com.codebeans.stockapi.model.entity.StockItem;

public class StockItemSpecifications {
    
    public static Specification<StockItem> createdBefore(LocalDate date) {
        return (root, query, cb) -> {
            return cb.lessThan(root.get("createdAt"), date.atStartOfDay());
        };
    }

    public static Specification<StockItem> createdAfter(LocalDate date) {
        return (root, query, cb) -> {
            return cb.greaterThan(root.get("createdAt"), date.atStartOfDay());
        };
    }

    public static Specification<StockItem> createdToday() {
        return (root, query, cb) -> {
            LocalDateTime today = LocalDate.now().atStartOfDay();
            LocalDateTime tomorrow = LocalDate.now().plusDays(1).atStartOfDay();

            return cb.and(
                cb.greaterThanOrEqualTo(root.get("createdAt"), today),
                cb.lessThan(root.get("createdAt"), tomorrow)
            );
        };
    }

    public static Specification<StockItem> minQuantity(Integer min) {
        return (root, query, cb) -> {
            return cb.greaterThanOrEqualTo(root.get("quantity"), min);
        };
    }
    
    public static Specification<StockItem> maxQuantity(Integer max) {
        return (root, query, cb) -> {
            return cb.lessThanOrEqualTo(root.get("quantity"), max);
        };
    }
}
