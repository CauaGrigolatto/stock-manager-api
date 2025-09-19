package br.com.codebeans.stockapi.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.codebeans.stockapi.model.entity.StockItem;

public interface StockItemRepository extends JpaRepository<StockItem, Integer>, JpaSpecificationExecutor<StockItem> {

    @Query("SELECT COUNT(DISTINCT i.category) FROM StockItem i")
    long countByCategories();

    @Query("""
        SELECT COUNT(i)
        FROM StockItem i
        WHERE DATE(i.createdAt) <= DATE(:start)
        AND   DATE(i.createdAt) >= DATE(:end)
    """)
    long countCreatedBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);

}
