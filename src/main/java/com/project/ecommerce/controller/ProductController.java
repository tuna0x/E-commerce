package com.project.ecommerce.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.ecommerce.domain.Product;
import com.project.ecommerce.domain.response.ResultPaginationDTO;
import com.project.ecommerce.service.ProductService;
import com.project.ecommerce.ultil.error.IdInvalidException;
import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) throws IdInvalidException {
        boolean check=this.productService.findByName(product.getName());
        if (check==true) {
            throw new IdInvalidException("name's exists");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(this.productService.handleCreate(product));
    }

    @PutMapping("/products")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) throws IdInvalidException{
        Product cur= this.productService.handleGetById(product.getId());
        if (cur == null) {
            throw new IdInvalidException("id is not exists");
        }
        return ResponseEntity.ok().body(this.productService.handleUpdate(product));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable ("id") long id) throws IdInvalidException{
        Product cur= this.productService.handleGetById(id);
        if (cur == null) {
            throw new IdInvalidException("id is not exists");
        }
        this.productService.handleDelete(id);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable ("id") long id) throws IdInvalidException{
        Product cur= this.productService.handleGetById(id);
        if (cur == null) {
            throw new IdInvalidException("id is not exists");
        }
        return ResponseEntity.ok().body(this.productService.handleGetById(id));
    }

    @GetMapping("/products")
    public ResponseEntity<ResultPaginationDTO> getAllProduct(@Filter Specification<Product> spec, Pageable page) {
        return ResponseEntity.ok().body(this.productService.handleGetAll(spec, page));
    }


}
