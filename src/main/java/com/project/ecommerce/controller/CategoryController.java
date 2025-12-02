package com.project.ecommerce.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

import com.project.ecommerce.domain.Category;
import com.project.ecommerce.domain.response.ResultPaginationDTO;
import com.project.ecommerce.service.CategoryService;
import com.project.ecommerce.ultil.annotation.APIMessage;
import com.project.ecommerce.ultil.error.IdInvalidException;
import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

     @PostMapping("/categories")
    @APIMessage("Create category is successful")
    public ResponseEntity<Category> createCategory(@Valid @RequestBody Category Category) throws IdInvalidException {
        boolean check=this.categoryService.findByName(Category.getName());
        if (check==true) {
            throw new IdInvalidException("name's exists");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(this.categoryService.handleCreate(Category));
    }

    @PutMapping("/categories")
    @APIMessage("Update category is successful")
    public ResponseEntity<Category> updateCategory(@RequestBody Category Category) throws IdInvalidException{
        Category cur= this.categoryService.handleGetById(Category.getId());
        if (cur == null) {
            throw new IdInvalidException("id is not exists");
        }
        return ResponseEntity.ok().body(this.categoryService.handleUpdate(Category));
    }

    @DeleteMapping("/categories/{id}")
    @APIMessage("Delete category is successful")
    public ResponseEntity<Void> deleteCategory(@PathVariable ("id") long id) throws IdInvalidException{
        Category cur= this.categoryService.handleGetById(id);
        if (cur == null) {
            throw new IdInvalidException("id is not exists");
        }
        this.categoryService.handleDelete(id);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/categories/{id}")
    @APIMessage("Get category by id is successful")
    public ResponseEntity<Category> getCategoryById(@PathVariable ("id") long id) throws IdInvalidException{
        Category cur= this.categoryService.handleGetById(id);
        if (cur == null) {
            throw new IdInvalidException("id is not exists");
        }
        return ResponseEntity.ok().body(this.categoryService.handleGetById(id));
    }

    @GetMapping("/categories")
    @APIMessage("Get all categories is successful")
    public ResponseEntity<ResultPaginationDTO> getAllCategory(@Filter Specification<Category> spec, Pageable page) {
        return ResponseEntity.ok().body(this.categoryService.handleGetAll(spec, page));
    }
}
