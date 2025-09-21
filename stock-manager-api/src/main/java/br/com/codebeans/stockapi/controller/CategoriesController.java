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

import br.com.codebeans.stockapi.model.dto.CategoriesFilterDTO;
import br.com.codebeans.stockapi.model.dto.ItemCategoryDTO;
import br.com.codebeans.stockapi.model.dto.PaginationResponseDTO;
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

    @GetMapping
    public ResponseEntity<?> getAll(CategoriesFilterDTO filter) throws Throwable {
        Page<ItemCategory> pages = categoryService.paginate(filter);
        Pageable pageable = pages.getPageable();
        
        List<ItemCategory> categories = pages.getContent();
        List<ItemCategoryDTO> categoriesDTO = itemCategoryMapper.toListDTO(categories);

        PaginationResponseDTO<List<ItemCategoryDTO>> response = new PaginationResponseDTO<List<ItemCategoryDTO>>();
        response.setStatus(HttpStatus.OK.value());
        response.setData(categoriesDTO);
        response.setMessage(null);
        response.setPage(pageable.getPageNumber());
        response.setPageSize(pageable.getPageSize());
        response.setTotalElements(pages.getTotalElements());
        response.setTotalPages(pages.getTotalPages());

        return ResponseEntity.ok(response);
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
