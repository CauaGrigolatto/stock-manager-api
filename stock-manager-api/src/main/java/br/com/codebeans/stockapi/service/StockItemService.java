package br.com.codebeans.stockapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.codebeans.stockapi.model.dto.CreationDateFiltersDTO;
import br.com.codebeans.stockapi.model.dto.ItemsFilterDTO;
import br.com.codebeans.stockapi.model.dto.PaginationFilterDTO;
import br.com.codebeans.stockapi.model.dto.QuantityFiltersDTO;
import br.com.codebeans.stockapi.model.entity.ItemCategory;
import br.com.codebeans.stockapi.model.entity.StockItem;
import br.com.codebeans.stockapi.model.specifications.StockItemSpecifications;
import br.com.codebeans.stockapi.repository.StockItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@SuppressWarnings({ "removal" })
public class StockItemService {
    @Autowired
    private StockItemRepository itemRepository;

    @Autowired
    private CategoryService categoryService;

    public void save(StockItem item) {
        Integer categoryId = item.getCategory().getId();
        ItemCategory category = categoryService.validateAndGetById(categoryId);
        item.setCategory(category);
        itemRepository.save(item);
    }

    public Optional<StockItem> findById(Integer id) {
        return itemRepository.findById(id);
    }

    public Page<StockItem> paginate(ItemsFilterDTO filter) {
        Pageable pageable = PaginationFilterDTO.buildPageable(filter);
        Specification<StockItem> spec = Specification.where(null);

        if (filter.hasValidName()) {
            spec = spec.and(StockItemSpecifications.nameLike(filter.getName()));
        }

        if (filter.isCreatedToday()) {
            spec = spec.and(StockItemSpecifications.createdToday());
        }
        else {
            if (filter.hasValidCreatedAfter()) {
                spec = spec.and(StockItemSpecifications.createdAfter(filter.getCreatedAfter()));
            }

            if (filter.hasValidCreatedBefore()) {
                spec = spec.and(StockItemSpecifications.createdBefore(filter.getCreatedBefore()));
            }
        }
        
        if (filter.hasValidMaxQuantity()) {
            spec = spec.and(StockItemSpecifications.maxQuantity(filter.getMaxQuantity()));
        }

        if (filter.hasValidMinQuantity()) {
            spec = spec.and(StockItemSpecifications.minQuantity(filter.getMinQuantity()));
        }

        if (filter.hasValidCategoryId()) {
            ItemCategory category = new ItemCategory();
            category.setId(filter.getCategoryId());
            spec = spec.and(StockItemSpecifications.categoryEquals(category));
        }

        if (filter.hasValidDescription()) {
            spec = spec.and(StockItemSpecifications.descriptionLike(filter.getDescription()));
        }

        return itemRepository.findAll(spec, pageable);
    }

    public long countAll() {
        return itemRepository.count();
    }

    public long countByCategories() {
        return itemRepository.countByCategories();
    }

    public long countByCreationDate(CreationDateFiltersDTO filters) {
        Specification<StockItem> specs = Specification.where(null);
        
        if (Boolean.TRUE.equals(filters.today())) {
            specs = specs.and(StockItemSpecifications.createdToday());
        }
        else {
            if (filters.after() != null) {
                specs = specs.and(StockItemSpecifications.createdAfter(filters.after()));
            }
    
            if (filters.before() != null) {
                specs = specs.and(StockItemSpecifications.createdBefore(filters.before()));
            }
        }
        
        return itemRepository.count(specs);
    }

    public long countByQuantity(QuantityFiltersDTO filters) {
        Specification<StockItem> specs = Specification.where(null);

        if (filters.min() != null) {
            specs = specs.and(StockItemSpecifications.minQuantity(filters.min()));
        }

        if (filters.max() != null) {
            specs = specs.and(StockItemSpecifications.maxQuantity(filters.max()));
        }

        return itemRepository.count(specs);
    }

    public void update(StockItem item) {
        StockItem prevItem = validateAndGetById(item.getId());
        item.setCreatedAt(prevItem.getCreatedAt());
        save(item);
    }

    public StockItem validateAndGetById(Integer id) {
        validateExistence(id);
        return findById(id).get();
    }

    public void delete(StockItem stockItem) {
        validateExistence(stockItem.getId());
        itemRepository.delete(stockItem);
    }

    public void validateExistence(Integer id) {
        boolean entityExists = itemRepository.existsById(id);
    
        if (! entityExists) {
            throw new EntityNotFoundException("Could not find item with id " + id + ".");
        }
    }
}
