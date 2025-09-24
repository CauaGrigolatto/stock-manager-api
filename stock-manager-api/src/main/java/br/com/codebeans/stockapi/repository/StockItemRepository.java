package br.com.codebeans.stockapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import br.com.codebeans.stockapi.model.entity.StockItem;

public interface StockItemRepository extends JpaRepository<StockItem, Integer>, JpaSpecificationExecutor<StockItem> {

    @Query("SELECT COUNT(DISTINCT i.category) FROM StockItem i")
    long countByCategories();

    @Query("SELECT SUM(i.quantity) FROM StockItem i")
    long countTotal();
    
}
