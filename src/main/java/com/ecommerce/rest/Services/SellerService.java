package com.ecommerce.rest.Services;

import com.ecommerce.rest.Dao.SellerDao;
import com.ecommerce.rest.Models.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerService {

    @Autowired
    private SellerDao sellerDao;

    public Seller getSellerById(String token, long sellerId) {
        if (sellerDao.findBySessionToken(token).isEmpty()) {
            throw new RuntimeException("Invalid session token");
        }
        return sellerDao.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller not found with ID: " + sellerId));
    }

    public Seller getCurrentSeller(String token) {
        return sellerDao.findBySessionToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid or expired session token"));
    }

    public List<Seller> getAllSellers() {
        return sellerDao.findAll();
    }

    public void addSeller(Seller seller) {
        sellerDao.save(seller);
    }

    public void updateSeller(String token, Seller updatedSeller) {
        Seller seller = getCurrentSeller(token);
        seller.setName(updatedSeller.getName());
        seller.setEmail(updatedSeller.getEmail());
        sellerDao.save(seller);
    }

    public void updatePassword(String token, String newPassword) {
        Seller seller = getCurrentSeller(token);
        seller.setPassword(newPassword);
        sellerDao.save(seller);
    }

    public void updateMobile(String token, String newMobile) {
        Seller seller = getCurrentSeller(token);
        seller.setMobileNumber(newMobile);
        sellerDao.save(seller);
    }

    public void deleteSeller(String token, long sellerId) {
        Seller seller = getSellerById(token, sellerId);
        sellerDao.delete(seller);
    }
}
