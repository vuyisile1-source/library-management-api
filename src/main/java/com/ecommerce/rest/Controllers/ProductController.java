package com.ecommerce.rest.Controllers;

import com.ecommerce.rest.Dto.QuantityUpdateRequest;
import com.ecommerce.rest.Models.Product;
import com.ecommerce.rest.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/product/{id}")
    public Product getProductById(@RequestHeader("Authorization") String token, @PathVariable long id) {
        return productService.getProductById(token, id);
    }

    @GetMapping("/products")
    public List<Product> getAllProducts(@RequestHeader("Authorization") String token) {
        return productService.getAllProducts(token);
    }

    @GetMapping("/products/{category}")
    public List<Product> getProductsByCategory(@RequestHeader("Authorization") String token, @PathVariable String category) {
        return productService.getProductsByCategory(token, category);
    }

    @GetMapping("/products/seller/{id}")
    public List<Product> getProductsBySellerId(@RequestHeader("Authorization") String token, @PathVariable long id) {
        return productService.getProductsBySellerId(token, id);
    }

    @PostMapping("/products")
    public String addProduct(@RequestBody Product product, @RequestHeader("Authorization") String token) {
        productService.addProduct(token, product);
        return "Product added successfully!";
    }

    @PutMapping("/products")
    public String updateProduct(@RequestBody Product product, @RequestHeader("Authorization") String token) {
        productService.updateProduct(token, product);
        return "Product updated successfully!";
    }

    @PutMapping("/products/{id}")
    public String updateProductQuantity(@RequestHeader("Authorization") String token, @PathVariable long id, @RequestBody QuantityUpdateRequest quantity) {
        productService.updateProductQuantity(token, id, quantity);
        return "Product quantity updated successfully!";
    }

    @DeleteMapping("/product/{id}")
    public String deleteProduct(@PathVariable long id, @RequestHeader("Authorization") String token) {
        productService.deleteProduct(token, id);
        return "Product deleted successfully!";
    }
}
