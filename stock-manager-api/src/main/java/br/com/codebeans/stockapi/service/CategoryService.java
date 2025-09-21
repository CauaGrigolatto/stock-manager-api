package br.com.codebeans.stockapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.codebeans.stockapi.model.dto.CategoriesFilterDTO;
import br.com.codebeans.stockapi.model.dto.PaginationFilterDTO;
import br.com.codebeans.stockapi.model.entity.ItemCategory;
import br.com.codebeans.stockapi.model.specifications.ItemCategorySpecifications;
import br.com.codebeans.stockapi.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public void save(ItemCategory category) throws Throwable {
        try {
            categoryRepository.save(category);
        }
        catch(Throwable t) {
            log.error("Error on saving category");
            throw t;
        }
    }

    public Optional<ItemCategory> findById(Integer id) throws Throwable {
        try {
            return categoryRepository.findById(id);
        }
        catch(Throwable t) {
            log.error("Error on finding category by id");
            throw t;
        }
    }

    public Page<ItemCategory> paginate(CategoriesFilterDTO filter) throws Throwable {
        try {
            Pageable pageable = PaginationFilterDTO.buildPageable(filter);
            Specification<ItemCategory> spec = Specification.where(null);

            if (filter.hasValidName()) {
                spec = spec.and(ItemCategorySpecifications.nameLike(filter.getName()));
            }

            return categoryRepository.findAll(spec, pageable);
        }
        catch(Throwable t) {
            log.error("Error on finding all categories");
            throw t;
        }
    }

    public void delete(ItemCategory category) throws Throwable {
        try {
            validateExistence(category.getId());
            categoryRepository.delete(category);
        }
        catch(EntityNotFoundException e) {
            throw e;
        }
        catch(Throwable t) {
            log.error("Error on deleting category");
            throw t;
        }
    }

    public ItemCategory validateAndGetById(Integer id) throws Throwable {
        try {
            validateExistence(id);
            return findById(id).get();
        }
        catch(EntityNotFoundException e) {
            throw e;
        }
        catch(Throwable t) {
            log.error("Error on getting category by ID", t);
            throw t;
        }
    }

    public void update(ItemCategory category) throws Throwable {
        try {
            validateExistence(category.getId());
            save(category);
        }
        catch(EntityNotFoundException e) {
            throw e;
        }
        catch(Throwable t) {
            log.error("Error on checking existence by ID", t);
            throw t;
        }
    }

    public void validateExistence(Integer id) throws Throwable {
        try {
            boolean entityExists = categoryRepository.existsById(id);

            if (! entityExists) {
                throw new EntityNotFoundException("Could not find category with id " + id + ".");
            }
        }
        catch(EntityNotFoundException e) {
            throw e;
        }
        catch(Throwable t) {
            log.error("Error on checking existence by ID", t);
            throw t;
        }
    }
}
