package br.com.codebeans.stockapi.controller;

import java.util.List;

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
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/items")
@Slf4j
public class ItemsController {

    @Autowired
    private StockItemMapper stockItemMapper;

    @Autowired
    private StockItemService itemService;

    @GetMapping
    public ResponseEntity<?> getAll(ItemsFilterDTO filters) throws Throwable {
        Page<StockItem> pages = itemService.paginate(filters);
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
        itemService.save(item);
        StockItemDTO itemDTO = stockItemMapper.toDTO(item);
        ResponseDTO<StockItemDTO> response = ResponseDTO.ok(itemDTO, "Item saved successfully.");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody @Valid SaveItemDTO saveItemRequest) throws Throwable  {
        StockItem item = stockItemMapper.toStockItem(saveItemRequest);
        item.setId(id);
        itemService.update(item);
        StockItemDTO itemDTO = stockItemMapper.toDTO(item);
        ResponseDTO<StockItemDTO> response = ResponseDTO.ok(itemDTO, "Item updated successfully.");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) throws Throwable  {
        StockItem item = new StockItem(id);
        itemService.delete(item);
        ResponseDTO<Void> response = ResponseDTO.ok("Item deleted successfully.");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Integer id) throws Throwable  {
        StockItem item = itemService.validateAndGetById(id);
        StockItemDTO itemDTO = stockItemMapper.toDTO( item );
        ResponseDTO<StockItemDTO> response = ResponseDTO.ok(itemDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/count")
    public ResponseEntity<?> countAll() throws Throwable  {
        long countResult = itemService.countAll();
        ResponseDTO<Long> response = ResponseDTO.ok(countResult);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/count-by-categories")
    public ResponseEntity<?> countByCategories() throws Throwable  {
        long countResult = itemService.countByCategories();
        ResponseDTO<Long> response = ResponseDTO.ok(countResult);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/count-by-creation-date")
    public ResponseEntity<?> countByCreationDate(CreationDateFiltersDTO filters) throws Throwable  {
        long countResult = itemService.countByCreationDate(filters);
        ResponseDTO<Long> response = ResponseDTO.ok(countResult);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/count-by-quantity")
    public ResponseEntity<?> countByQuantity(QuantityFiltersDTO filters) throws Throwable  {
        long countResult = itemService.countByQuantity(filters);
        ResponseDTO<Long> response = ResponseDTO.ok(countResult);
        return ResponseEntity.ok(response);
    }
}
