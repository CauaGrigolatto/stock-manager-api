package br.com.codebeans.stockapi.service;

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
            log.error("Error on saving category", t);
            throw t;
        }
    }
}
