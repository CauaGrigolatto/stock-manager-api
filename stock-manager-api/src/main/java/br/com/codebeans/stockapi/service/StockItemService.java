package br.com.codebeans.stockapi.service;

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
}
