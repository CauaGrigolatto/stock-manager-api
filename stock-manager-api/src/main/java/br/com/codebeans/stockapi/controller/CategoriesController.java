package br.com.codebeans.stockapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import br.com.codebeans.stockapi.model.dto.CategoriesFilterDTO;
import br.com.codebeans.stockapi.model.dto.ItemCategoryDTO;
import br.com.codebeans.stockapi.model.dto.PaginationResponseDTO;
import br.com.codebeans.stockapi.model.dto.ResponseDTO;
import br.com.codebeans.stockapi.model.dto.SaveCategoryDTO;
import br.com.codebeans.stockapi.model.entity.ItemCategory;
import br.com.codebeans.stockapi.model.mapper.CategoryMapper;
import br.com.codebeans.stockapi.service.CategoryService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/categories")
@Slf4j
public class CategoriesController {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> getAll(CategoriesFilterDTO filter) throws Throwable {
        Page<ItemCategory> page = categoryService.paginate(filter);
        PaginationResponseDTO<List<ItemCategoryDTO>> response = PaginationResponseDTO.buildResponse(page, categoryMapper);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid SaveCategoryDTO saveCategoryRequest) throws Throwable {
        ItemCategory category = categoryMapper.toItemCategory(saveCategoryRequest);
        categoryService.save(category);
        ItemCategoryDTO categoryDTO = categoryMapper.toDTO(category);
        ResponseDTO<ItemCategoryDTO> response = ResponseDTO.ok(categoryDTO, "Categoria salva com sucesso.");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody @Valid SaveCategoryDTO saveCategoryRequest) throws Throwable {
        ItemCategory category = categoryMapper.toItemCategory(saveCategoryRequest);
        category.setId(id);
        categoryService.update(category);
        
        ItemCategoryDTO categoryDTO = categoryMapper.toDTO(category);
        ResponseDTO<ItemCategoryDTO> response = ResponseDTO.ok(categoryDTO, "Categoria atualizada com sucesso.");
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Integer id) throws Throwable {
        ItemCategory category = categoryService.validateAndGetById(id);
        ItemCategoryDTO categoryDTO = categoryMapper.toDTO( category );
        ResponseDTO<ItemCategoryDTO> response = ResponseDTO.ok(categoryDTO);
        return ResponseEntity.ok( response );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) throws Throwable {
        ItemCategory category = new ItemCategory(id);
        categoryService.delete(category);
        
        ResponseDTO<Void> response = new ResponseDTO<>(
            HttpStatus.OK.value(),
            "Categoria deletada com sucesso.",
            null
        );

        return ResponseEntity.ok(response);
    }
}
