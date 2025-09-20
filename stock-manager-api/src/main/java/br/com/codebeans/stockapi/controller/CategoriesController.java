package br.com.codebeans.stockapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import br.com.codebeans.stockapi.model.dto.ItemCategoryDTO;
import br.com.codebeans.stockapi.model.dto.ResponseDTO;
import br.com.codebeans.stockapi.model.dto.SaveCategoryDTO;
import br.com.codebeans.stockapi.model.entity.ItemCategory;
import br.com.codebeans.stockapi.model.mapper.ItemCategoryMapper;
import br.com.codebeans.stockapi.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/categories")
@Slf4j
public class CategoriesController {

    @Autowired
    private ItemCategoryMapper itemCategoryMapper;

    @Autowired
    private CategoryService categoryService;

    //TODO implementar filtros para pagination e unpaged 
    @GetMapping
    public ResponseEntity<?> getAll() throws Throwable {
        List<ItemCategory> categories = categoryService.findAll();
        List<ItemCategoryDTO> categoriesDTO = itemCategoryMapper.toListDTO(categories);
        return ResponseEntity.ok(categoriesDTO);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid SaveCategoryDTO saveCategoryRequest) throws Throwable {
        ItemCategory category = itemCategoryMapper.toItemCategory(saveCategoryRequest);
        categoryService.save(category);
        ItemCategoryDTO categoryDTO = itemCategoryMapper.toDTO(category);
        ResponseDTO<ItemCategoryDTO> response = ResponseDTO.ok(categoryDTO, "Category saved successfully.");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody @Valid SaveCategoryDTO saveCategoryRequest) throws Throwable {
        categoryService.findById(id).orElseThrow(() ->  new EntityNotFoundException("Could not find category with id " + id + "."));

        ItemCategory category = itemCategoryMapper.toItemCategory(saveCategoryRequest);
        category.setId(id);
        categoryService.save(category);
        
        ItemCategoryDTO categoryDTO = itemCategoryMapper.toDTO(category);
        ResponseDTO<ItemCategoryDTO> response = ResponseDTO.ok(categoryDTO, "Category updated successfully.");
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Integer id) throws Throwable {
        ItemCategory category = categoryService.findById(id).orElseThrow(() -> new EntityNotFoundException("Could not find category with id " + id + "."));

        ItemCategoryDTO categoryDTO = itemCategoryMapper.toDTO( category );

        ResponseDTO<ItemCategoryDTO> response = ResponseDTO.ok(categoryDTO);

        return ResponseEntity.ok( response );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) throws Throwable {
        ItemCategory category = categoryService.findById(id).orElseThrow(() -> new EntityNotFoundException("Could not find category with id " + id + "."));
        
        categoryService.delete(category);

        ResponseDTO<Void> response = new ResponseDTO<>(
            HttpStatus.OK.value(),
            "Category deleted successfully.",
            null
        );

        return ResponseEntity.ok(response);
    }
}
