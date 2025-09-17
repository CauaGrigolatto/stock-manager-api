package br.com.codebeans.stockapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.codebeans.stockapi.model.dto.SaveItemRequest;
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

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid SaveItemRequest saveItemRequest, BindingResult result) {
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
}
