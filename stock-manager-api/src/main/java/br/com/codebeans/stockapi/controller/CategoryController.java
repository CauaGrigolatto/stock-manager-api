package br.com.codebeans.stockapi.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import br.com.codebeans.stockapi.model.dto.ItemCategoryDTO;
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

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
        @PathVariable Integer id,
        @RequestBody @Valid SaveCategoryRequest saveCategoryRequest,
        BindingResult result
    ) {
        try {
            if (result.hasErrors()) {
                return new ResponseEntity<Void>(HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()));
            }

            Optional<ItemCategory> optCategoty = categoryService.findById(id);

            if (optCategoty.isEmpty()) {
                return new ResponseEntity<Void>(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
            }

            ItemCategory category = itemCategoryMapper.toItemCategory(saveCategoryRequest);
            category.setId(id);
            categoryService.save(category);
            return new ResponseEntity<Void>(HttpStatusCode.valueOf(HttpStatus.OK.value()));
        }
        catch(Throwable t) {
            log.error("Error on updating category", t);
            return new ResponseEntity<Void>(HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Integer id) {
        try {
            Optional<ItemCategory> optCategory = categoryService.findById(id);

            if (optCategory.isEmpty()) {
                return new ResponseEntity<Void>(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
            }

            ItemCategoryDTO categoryDTO = itemCategoryMapper.toDTO( optCategory.get() );

            return ResponseEntity.ok( categoryDTO );
        }
        catch(Throwable t) {
            log.error("Error on finding category by id", t);
            return new ResponseEntity<Void>(HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}
