package br.com.codebeans.stockapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void save(StockItem item) {
        Integer categoryId = item.getCategory().getId();
        ItemCategory category = categoryService.validateAndGetById(categoryId);
        item.setCategory(category);
        itemRepository.save(item);
    }

    @Transactional(readOnly = true)
    public Optional<StockItem> findById(Integer id) {
        return itemRepository.findById(id);
    }

    @Transactional(readOnly = true)
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
                spec = spec.and(StockItemSpecifications.asFromAfter(filter.getCreatedAfter()));
            }

            if (filter.hasValidCreatedBefore()) {
                spec = spec.and(StockItemSpecifications.asFromBefore(filter.getCreatedBefore()));
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

    @Transactional(readOnly = true)
    public long countTotal() {
        return itemRepository.countTotal();
    }

    @Transactional(readOnly = true)
    public long countByCategories() {
        return itemRepository.countByCategories();
    }

    @Transactional(readOnly = true)
    public long countByCreationDate(CreationDateFiltersDTO filters) {
        Specification<StockItem> specs = Specification.where(null);
        
        if (Boolean.TRUE.equals(filters.today())) {
            specs = specs.and(StockItemSpecifications.createdToday());
        }
        else {
            if (filters.after() != null) {
                specs = specs.and(StockItemSpecifications.asFromAfter(filters.after()));
            }
    
            if (filters.before() != null) {
                specs = specs.and(StockItemSpecifications.asFromBefore(filters.before()));
            }
        }
        
        return itemRepository.count(specs);
    }

    @Transactional(readOnly = true)
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

    @Transactional
    public void update(StockItem item) {
        StockItem prevItem = validateAndGetById(item.getId());
        item.setCreatedAt(prevItem.getCreatedAt());
        itemRepository.save(item);
    }

    @Transactional(readOnly = true)
    public StockItem validateAndGetById(Integer id) {
        validateExistence(id);
        return findById(id).get();
    }

    @Transactional
    public void delete(StockItem stockItem) {
        validateExistence(stockItem.getId());
        itemRepository.delete(stockItem);
    }

    @Transactional(readOnly = true)
    public void validateExistence(Integer id) {
        boolean entityExists = itemRepository.existsById(id);
    
        if (! entityExists) {
            throw new EntityNotFoundException("Could not find item with id " + id + ".");
        }
    }
}
