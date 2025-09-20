package br.com.codebeans.stockapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.codebeans.stockapi.model.dto.CreationDateFiltersDTO;
import br.com.codebeans.stockapi.model.dto.ItemsFilterDTO;
import br.com.codebeans.stockapi.model.dto.PaginationResponseDTO;
import br.com.codebeans.stockapi.model.dto.QuantityFiltersDTO;
import br.com.codebeans.stockapi.model.dto.ResponseDTO;
import br.com.codebeans.stockapi.model.dto.SaveItemDTO;
import br.com.codebeans.stockapi.model.dto.StockItemDTO;
import br.com.codebeans.stockapi.model.entity.StockItem;
import br.com.codebeans.stockapi.model.mapper.StockItemMapper;
import br.com.codebeans.stockapi.service.StockItemService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/items")
@Slf4j
public class ItemsController {

    @Autowired
    private StockItemMapper stockItemMapper;

    @Autowired
    private StockItemService stockItemService;

    @GetMapping
    public ResponseEntity<?> getAll(ItemsFilterDTO filters) throws Throwable {
        Page<StockItem> pages = stockItemService.paginate(filters);
        Pageable pageable = pages.getPageable();

        List<StockItem> items = pages.getContent();
        List<StockItemDTO> itemsDTO = stockItemMapper.toListDTO(items);

        PaginationResponseDTO<List<StockItemDTO>> response = new PaginationResponseDTO<List<StockItemDTO>>();
        response.setStatus(HttpStatus.OK.value());
        response.setData(itemsDTO);
        response.setMessage(null);
        response.setPage(pageable.getPageNumber());
        response.setPageSize(pageable.getPageSize());
        response.setTotalElements(pages.getTotalElements());
        response.setTotalPages(pages.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid SaveItemDTO saveItemRequest) throws Throwable  {
        StockItem item = stockItemMapper.toStockItem(saveItemRequest);
        stockItemService.save(item);
        StockItemDTO itemDTO = stockItemMapper.toDTO(item);

        ResponseDTO<StockItemDTO> response = ResponseDTO.ok(itemDTO, "Item saved successfully.");

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody @Valid SaveItemDTO saveItemRequest) throws Throwable  {
        Optional<StockItem> optItem = stockItemService.findById(id);

        if (optItem.isEmpty()) {
            throw new EntityNotFoundException("Could not find item with id " + id + ".");
        }
        
        StockItem prevItem = optItem.get();

        StockItem item = stockItemMapper.toStockItem(saveItemRequest);
        item.setId(prevItem.getId());
        item.setCreatedAt(prevItem.getCreatedAt());
        
        stockItemService.save(item);

        StockItem updatedItem = stockItemService.findById(id).get();
        StockItemDTO itemDTO = stockItemMapper.toDTO(updatedItem);

        ResponseDTO<StockItemDTO> response = ResponseDTO.ok(itemDTO, "Item updated successfully.");

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) throws Throwable  {
        Optional<StockItem> optItem = stockItemService.findById(id);

        if (optItem.isEmpty()) {
            throw new EntityNotFoundException("Could not find item with id " + id + ".");
        }
        
        stockItemService.delete(optItem.get());

        ResponseDTO<Void> response = new ResponseDTO<>(
            HttpStatus.OK.value(),
            "Item deleted successfully.",
            null
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Integer id) throws Throwable  {
        Optional<StockItem> optItem = stockItemService.findById(id);

        if (optItem.isEmpty()) {
            throw new EntityNotFoundException("Could not find item with id " + id + ".");
        }

        StockItemDTO itemDTO = stockItemMapper.toDTO( optItem.get() );

        ResponseDTO<StockItemDTO> response = ResponseDTO.ok(itemDTO);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/count")
    public ResponseEntity<?> countAll() throws Throwable  {
        long countResult = stockItemService.countAll();
        ResponseDTO<Long> response = ResponseDTO.ok(countResult);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/count-by-categories")
    public ResponseEntity<?> countByCategories() throws Throwable  {
        long countResult = stockItemService.countByCategories();
        ResponseDTO<Long> response = ResponseDTO.ok(countResult);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/count-by-creation-date")
    public ResponseEntity<?> countByCreationDate(CreationDateFiltersDTO filters) throws Throwable  {
        long countResult = stockItemService.countByCreationDate(filters);
        ResponseDTO<Long> response = ResponseDTO.ok(countResult);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/count-by-quantity")
    public ResponseEntity<?> countByQuantity(QuantityFiltersDTO filters) throws Throwable  {
        long countResult = stockItemService.countByQuantity(filters);
        ResponseDTO<Long> response = ResponseDTO.ok(countResult);
        return ResponseEntity.ok(response);
    }
}
