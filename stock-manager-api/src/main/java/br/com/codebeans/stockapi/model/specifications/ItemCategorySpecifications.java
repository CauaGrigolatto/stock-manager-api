package br.com.codebeans.stockapi.model.specifications;

import org.springframework.data.jpa.domain.Specification;

import br.com.codebeans.stockapi.model.entity.ItemCategory;

public class ItemCategorySpecifications {
    public static Specification<ItemCategory> nameLike(String name) {
        return (root, query, cb) -> {
            return cb.like(root.get("name"), "%" + name + "%");
        };
    }
}
