package com.ecommerce.rest.Services;

import com.ecommerce.rest.Dao.ProductDao;
import com.ecommerce.rest.Dao.SellerDao;
import com.ecommerce.rest.Dto.QuantityUpdateRequest;
import com.ecommerce.rest.Models.Product;
import com.ecommerce.rest.Models.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private SellerDao sellerDao;

    public Product getProductById(String token, long id) {
        Seller user = validateSeller(token);
        return productDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));
    }

    public List<Product> getAllProducts(String token) {
        Seller user = validateSeller(token);
        return productDao.findAll();
    }

    public List<Product> getProductsByCategory(String token, String category) {
        Seller user = validateSeller(token);
        return productDao.findByCategory(category);
    }

    public List<Product> getProductsBySellerId(String token, long sellerId) {
        Seller user = validateSeller(token);
        return productDao.findBySellerId(sellerId);
    }

    public void addProduct(String token, Product product) {
        Seller user = validateSeller(token);
        product.setSeller(user);
        productDao.save(product);
    }

    public void updateProduct(String token, Product updatedProduct) {
        Seller user = validateSeller(token);
        Product existingProduct = productDao.findById(updatedProduct.getId())
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + updatedProduct.getId()));

        if (!existingProduct.getSeller().equals(user)) {
            throw new RuntimeException("You do not have permission to update this product.");
        }

        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setQuantity(updatedProduct.getQuantity());
        existingProduct.setCategory(updatedProduct.getCategory());
        productDao.save(existingProduct);
    }

    public void updateProductQuantity(String token, long productId, QuantityUpdateRequest quantity) {
        Seller user = validateSeller(token);
        Product product = productDao.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

        if (!product.getSeller().equals(user)) {
            throw new RuntimeException("You do not have permission to update this product.");
        }
        System.out.println(quantity);
        product.setQuantity(quantity.getQuantity());
        productDao.save(product);
    }

    public void deleteProduct(String token, long productId) {
        Seller user = validateSeller(token);
        Product product = productDao.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

        if (!product.getSeller().equals(user)) {
            throw new RuntimeException("You do not have permission to delete this product.");
        }

        productDao.delete(product);
    }

    private Seller validateSeller(String token) {
        return sellerDao.findBySessionToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid or expired session token."));
    }
}
