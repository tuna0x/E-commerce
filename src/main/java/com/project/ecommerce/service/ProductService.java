package com.project.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.project.ecommerce.domain.Product;
import com.project.ecommerce.domain.response.ResultPaginationDTO;
import com.project.ecommerce.repository.ProductRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product handleCreate(Product product){
            return this.productRepository.save(product);
    }

    public Product handleGetById(long id){
        Optional<Product> product=this.productRepository.findById(id);
        return product.isPresent() ? product.get() : null;
    }

    public Product handleUpdate(Product product){
        Product cur = this.handleGetById(product.getId());
        if (cur != null) {
            cur.setId(product.getId());
            cur.setName(product.getName());
            cur.setDescription(product.getDescription());
            cur.setImage(product.getImage());
            cur.setOriginalPrice(product.getOriginalPrice());
            cur.setDiscountedPrice(product.getDiscountedPrice());
            cur.setStock(product.getStock());
            cur.setCategory(product.getCategory());
            product=this.productRepository.save(cur);
        }
        return product;
    }

    public void handleDelete(long id){
        this.productRepository.deleteById(id);
    }

    public ResultPaginationDTO handleGetAll(Specification<Product> spec,Pageable page){
         Page<Product> product= this.productRepository.findAll(spec, page);
        ResultPaginationDTO rs=new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta=new ResultPaginationDTO.Meta();
        meta.setPage(product.getNumber() + 1);
        meta.setPageSize(product.getSize());
        meta.setPages(product.getTotalPages());
        meta.setTotal(product.getTotalElements());


        rs.setResult(product.getContent());
        return rs;
    }

    public boolean findByName(String name){
        return this.productRepository.existsByName(name);
    }

    public double findByOriginalPrice(long id){
        return this.findByOriginalPrice(id);
    }

}
