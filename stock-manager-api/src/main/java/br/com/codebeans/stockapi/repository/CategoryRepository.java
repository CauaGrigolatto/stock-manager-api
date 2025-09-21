package br.com.codebeans.stockapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.codebeans.stockapi.model.entity.ItemCategory;

public interface CategoryRepository extends JpaRepository<ItemCategory, Integer>, JpaSpecificationExecutor<ItemCategory> {

}
