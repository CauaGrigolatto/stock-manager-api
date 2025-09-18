package br.com.codebeans.stockapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.codebeans.stockapi.model.entity.StockItem;
import br.com.codebeans.stockapi.repository.StockItemRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StockItemService {
    @Autowired
    private StockItemRepository itemRepository;

    public void save(StockItem item) throws Throwable {
        try {
            itemRepository.save(item);
        }
        catch(Throwable t) {
            log.error("Error on saving item");
            throw t;
        }
    }

    public Optional<StockItem> findById(Integer id) {
        try {
            return itemRepository.findById(id);
        }
        catch(Throwable t) {
            log.error("Error on finding item by id");
            throw t;
        }
    }

    public List<StockItem> findAll() {
        try {
            return itemRepository.findAll();
        }
        catch(Throwable t) {
            log.error("Error on finding all items");
            throw t;
        }
    }

    public long countAll() {
        try {
            return itemRepository.count();
        }
        catch(Throwable t) {
            log.error("Error on counting all items");
            throw t;
        }
    }

    public long countByCategories() {
        try {
            return itemRepository.countByCategories();
        }
        catch(Throwable t) {
            log.error("Error on counting by categories");
            throw t;
        }
    }
}
