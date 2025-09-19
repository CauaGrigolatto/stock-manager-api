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

    public void save(StockItem item) throws Throwable {
        try {
            Integer categoryId = item.getCategory().getId();
            Optional<ItemCategory> optCategory = categoryService.findById(categoryId);

            if (optCategory.isEmpty()) {
                throw new EntityNotFoundException("Could not find category with id " + categoryId + ".");
            }

            itemRepository.save(item);
        }
        catch(Throwable t) {
            log.error("Error on saving item");
            throw t;
        }
    }

    public Optional<StockItem> findById(Integer id) throws Throwable {
        try {
            return itemRepository.findById(id);
        }
        catch(Throwable t) {
            log.error("Error on finding item by id");
            throw t;
        }
    }

    public Page<StockItem> paginate(ItemsFilterDTO filters) throws Throwable {
        try {
            Pageable pageable = PaginationFilterDTO.buildPageable(filters);
            Specification<StockItem> spec = Specification.where(null);

            if (filters.hasValidName()) {
                spec = spec.and(StockItemSpecifications.nameLike(filters.getName()));
            }

            if (filters.isCreatedToday()) {
                spec = spec.and(StockItemSpecifications.createdToday());
            }
            else {
                if (filters.hasValidMaxQuantity()) {
                    spec = spec.and(StockItemSpecifications.maxQuantity(filters.getMaxQuantity()));
                }
    
                if (filters.hasValidMinQuantity()) {
                    spec = spec.and(StockItemSpecifications.minQuantity(filters.getMinQuantity()));
                }
            }

            if (filters.hasValidCategoryId()) {
                ItemCategory category = new ItemCategory();
                category.setId(filters.getCategoryId());
                spec = spec.and(StockItemSpecifications.categoryEquals(category));
            }

            if (filters.hasValidDescription()) {
                spec = spec.and(StockItemSpecifications.descriptionLike(filters.getDescription()));
            }

            return itemRepository.findAll(spec, pageable);
        }
        catch(Throwable t) {
            log.error("Error on finding all items");
            throw t;
        }
    }

    

    public long countAll() throws Throwable {
        try {
            return itemRepository.count();
        }
        catch(Throwable t) {
            log.error("Error on counting all items");
            throw t;
        }
    }

    public long countByCategories() throws Throwable {
        try {
            return itemRepository.countByCategories();
        }
        catch(Throwable t) {
            log.error("Error on counting by categories");
            throw t;
        }
    }

    public long countByCreationDate(CreationDateFiltersDTO filters) throws Throwable {
        try {
            Specification<StockItem> specs = Specification.where(null);
            
            if (filters.today()) {
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
        catch(Throwable t) {
            log.error("Error on counting by creation date");
            throw t;
        }
    }

    public long countByQuantity(QuantityFiltersDTO filters) {
        try {
            Specification<StockItem> specs = Specification.where(null);

            if (filters.min() != null) {
                specs = specs.and(StockItemSpecifications.minQuantity(filters.min()));
            }

            if (filters.max() != null) {
                specs = specs.and(StockItemSpecifications.maxQuantity(filters.max()));
            }

            return itemRepository.count(specs);
        }
        catch(Throwable t) {
            log.error("Error on counting by quantity");
            throw t;
        }
    }
}
