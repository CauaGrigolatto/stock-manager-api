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

import br.com.codebeans.stockapi.model.dto.SaveCategoryRequest;
import br.com.codebeans.stockapi.model.entity.ItemCategory;
import br.com.codebeans.stockapi.model.mapper.ItemCategoryMapper;
import br.com.codebeans.stockapi.service.CategoryService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/categories")
@Slf4j
public class CategoryController {

    @Autowired
    private ItemCategoryMapper itemCategoryMapper;

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid SaveCategoryRequest saveCategoryRequest, BindingResult result) {
        try {
            if (result.hasErrors()) {
                return new ResponseEntity<Void>(HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()));
            }

            ItemCategory category = itemCategoryMapper.toItemCategory(saveCategoryRequest);
            categoryService.save(category);
            return new ResponseEntity<Void>(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        }
        catch(Throwable t) {
            log.error("Error on saving category", t);
            return new ResponseEntity<Void>(HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}
