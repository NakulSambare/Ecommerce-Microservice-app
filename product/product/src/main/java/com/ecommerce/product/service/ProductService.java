package com.ecommerce.product.service;


import com.ecommerce.product.dto.ProductRequest;
import com.ecommerce.product.dto.ProductResponse;
import com.ecommerce.product.model.Product;
import com.ecommerce.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse createProduct(ProductRequest productRequest) {
       Product p = new Product();
       updateProductFromRequest(p, productRequest);
      Product savedProduct =  productRepository.save(p);
      return mapToProductResponse(savedProduct);
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductResponse> responses = new ArrayList<>();
        for (Product p : products) {
            responses.add(mapToProductResponse(p));
        }
        return responses;
    }

    public ProductResponse getProductById(Long id) {
        Product p = productRepository.findById(id).orElse(null);
        if (p == null) return null;
        return mapToProductResponse(p);
    }

    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        return productRepository.findById(id).map(existing -> {
            updateProductFromRequest(existing, productRequest);
            Product saved = productRepository.save(existing);
            return mapToProductResponse(saved);
        }).orElse(null);
    }

    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private ProductResponse mapToProductResponse(Product savedProduct) {
        ProductResponse response = new ProductResponse();
        response.setId(savedProduct.getId());
        response.setName(savedProduct.getName());
        response.setDescription(savedProduct.getDescription());
        response.setPrice(savedProduct.getPrice());
        response.setActive(savedProduct.getActive());
        response.setCategory(savedProduct.getCategory());
        response.setImageUrl(savedProduct.getImageUrl());
        response.setStockQuantity(savedProduct.getStockQuantity());

        return response;
    }

    private void updateProductFromRequest(Product p, ProductRequest productRequest) {
                p.setName(productRequest.getName());
                p.setDescription(productRequest.getDescription());
                p.setPrice(productRequest.getPrice());
                 p.setCategory(productRequest.getCategory());
                p.setImageUrl(productRequest.getImageUrl());
                p.setStockQuantity(productRequest.getStockQuantity());

    }


    public List<ProductResponse> searchProducts(String query) {
        List<Product> products = productRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query);
        List<ProductResponse> responses = new ArrayList<>();
        for (Product p : products) {
            responses.add(mapToProductResponse(p));
        }
        return responses;
    }
}
