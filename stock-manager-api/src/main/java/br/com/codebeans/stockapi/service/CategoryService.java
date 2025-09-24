package br.com.codebeans.stockapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void save(ItemCategory category) {
        categoryRepository.save(category);
    }

    @Transactional(readOnly = true)
    public Optional<ItemCategory> findById(Integer id) {
        return categoryRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Page<ItemCategory> paginate(CategoriesFilterDTO filter) {
        Pageable pageable = PaginationFilterDTO.buildPageable(filter);
        Specification<ItemCategory> spec = Specification.where(null);

        if (filter.hasValidName()) {
            spec = spec.and(ItemCategorySpecifications.nameLike(filter.getName()));
        }
        
        return categoryRepository.findAll(spec, pageable);
    }

    @Transactional
    public void delete(ItemCategory category) {
        validateExistence(category.getId());
        categoryRepository.delete(category);
    }

    @Transactional(readOnly = true)
    public ItemCategory validateAndGetById(Integer id) {
        validateExistence(id);
        return findById(id).get();
    }

    @Transactional
    public void update(ItemCategory category) {
        validateExistence(category.getId());
        save(category);
    }

    @Transactional(readOnly = true)
    public void validateExistence(Integer id) {
        boolean entityExists = categoryRepository.existsById(id);

        if (! entityExists) {
            throw new EntityNotFoundException("Could not find category with id " + id + ".");
        }
    }
}
