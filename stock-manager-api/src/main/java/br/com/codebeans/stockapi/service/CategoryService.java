package br.com.codebeans.stockapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.codebeans.stockapi.model.entity.ItemCategory;
import br.com.codebeans.stockapi.repository.CategoryRepository;
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

    public List<ItemCategory> findAll() throws Throwable {
        try {
            return categoryRepository.findAll();
        }
        catch(Throwable t) {
            log.error("Error on finding all categories");
            throw t;
        }
    }

    public void delete(ItemCategory category) {
        try {
            categoryRepository.delete(category);
        }
        catch(Throwable t) {
            log.error("Error on deleting category");
            throw t;
        }
    }
}
