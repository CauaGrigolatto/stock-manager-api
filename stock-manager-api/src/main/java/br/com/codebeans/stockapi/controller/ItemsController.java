package br.com.codebeans.stockapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
    private StockItemService stockItemService;

    @GetMapping
    public ResponseEntity<?> getAll(ItemsFilterDTO filters) {
        try {
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
        catch(Throwable t) {
            log.error("Error on getting all items", t);
            return new ResponseEntity<Void>(HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid SaveItemDTO saveItemRequest, BindingResult result) {
        try {
            if (result.hasErrors()) {
                return new ResponseEntity<Void>(HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()));
            }

            StockItem item = stockItemMapper.toStockItem(saveItemRequest);
            stockItemService.save(item);
            return new ResponseEntity<Void>(HttpStatusCode.valueOf(HttpStatus.OK.value()));

        } catch (Throwable t) {
            log.error("Error on saving item", t);
            return new ResponseEntity<Void>(HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
        @PathVariable Integer id, 
        @RequestBody @Valid SaveItemDTO saveItemRequest, 
        BindingResult result) {
        
        try {
            if (result.hasErrors()) {
                return new ResponseEntity<Void>(HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()));
            }

            Optional<StockItem> optItem = stockItemService.findById(id);

            if (optItem.isEmpty()) {
                return new ResponseEntity<Void>(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));    
            }
            
            StockItem item = stockItemMapper.toStockItem(saveItemRequest);
            item.setId(id);
            stockItemService.save(item);
            return new ResponseEntity<Void>(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        }
        catch(Throwable t) {
            log.error("Error on updating item", t);
            return new ResponseEntity<Void>(HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Integer id) {
        try {
            Optional<StockItem> optItem = stockItemService.findById(id);

            if (optItem.isEmpty()) {
                return new ResponseEntity<Void>(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
            }

            StockItemDTO itemDTO = stockItemMapper.toDTO( optItem.get() );
            return ResponseEntity.ok(itemDTO);
        }
        catch(Throwable t) {
            log.error("Error on finding item by id", t);
            return new ResponseEntity<Void>(HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @GetMapping("/count")
    public ResponseEntity<?> countAll() {
        try {
            long countResult = stockItemService.countAll();
            return ResponseEntity.ok(countResult);
        }
        catch(Throwable t) {
            log.error("Error on counting all items", t);
            return new ResponseEntity<Void>(HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @GetMapping("/count-by-categories")
    public ResponseEntity<?> countByCategories() {
        try {
            long countResult = stockItemService.countByCategories();
            return ResponseEntity.ok(countResult);
        }
        catch(Throwable t) {
            log.error("Error on counting by categories", t);
            return new ResponseEntity<Void>(HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @GetMapping("/count-by-creation-date")
    public ResponseEntity<?> countByCreationDate(CreationDateFiltersDTO filters) {
        try {
            long countResult = stockItemService.countByCreationDate(filters);
            return ResponseEntity.ok(countResult);
        }
        catch (Throwable t) {
            log.error("Error on counting created items between dates", t);
            return new ResponseEntity<Void>(HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @GetMapping("/count-by-quantity")
    public ResponseEntity<?> countByQuantity(QuantityFiltersDTO filters) {
        try {
            long countResult = stockItemService.countByQuantity(filters);
            return ResponseEntity.ok(countResult);
        }
        catch (Throwable t) {
            log.error("Error on counting by quantity", t);
            return new ResponseEntity<Void>(HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}
