package br.com.codebeans.stockapi.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="stock_item")
@Data
@NoArgsConstructor
public class StockItem {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="item_id")
    private Integer id;

    @Column(name="name", length=255)
    @Basic(optional=false)
    private String name;

    @Column(name="quantity")
    @Basic(optional=false)
    private Integer quantity;

    @Column(name="price")
    @Basic(optional=false)
    private BigDecimal price;

    @ManyToOne(fetch=FetchType.EAGER, optional=false)
    @JoinColumn(name="category_id")
    private ItemCategory category;

    @Column(name="description", length=255)
    @Basic(optional=false)
    private String description;

    @Column(name="created_at")
    @Basic(optional=false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    public StockItem(Integer id) {
        this.id = id;
    }
}
