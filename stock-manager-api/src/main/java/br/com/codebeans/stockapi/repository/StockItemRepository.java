package br.com.codebeans.stockapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.codebeans.stockapi.model.entity.StockItem;

public interface StockItemRepository extends JpaRepository<StockItem, Integer> {

}
