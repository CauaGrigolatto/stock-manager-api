package br.com.codebeans.stockapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.codebeans.stockapi.model.entity.ItemCategory;

public interface CategoryRepository extends JpaRepository<ItemCategory, Integer> {

}
