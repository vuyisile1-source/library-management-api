package com.ecommerce.rest.Controllers;

import com.ecommerce.rest.Models.Seller;
import com.ecommerce.rest.Services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ecommerce.rest.Models.User.Role.SELLER;

@RestController
public class SellerController {

    @Autowired
    private SellerService sellerService;

    @GetMapping("/seller/{sellerid}")
    public Seller getSellerById(@RequestHeader("Authorization") String token, @PathVariable long sellerid) {
        return sellerService.getSellerById(token, sellerid);
    }

    @GetMapping("/seller/current")
    public Seller getCurrentSeller(@RequestHeader("Authorization") String token) {
        return sellerService.getCurrentSeller(token);
    }

    @GetMapping("/sellers")
    public List<Seller> getAllSellers() {
        return sellerService.getAllSellers();
    }

    @PostMapping("/addseller")
    public String addSeller(@RequestBody Seller seller) {
        seller.setRole(SELLER);
        sellerService.addSeller(seller);
        return "Seller added successfully!";
    }

    @PutMapping("/seller")
    public String updateSeller(@RequestHeader("Authorization") String token, @RequestBody Seller seller) {
        sellerService.updateSeller(token, seller);
        return "Seller updated successfully!";
    }

    @PutMapping("/seller/update/password")
    public String updateSellerPassword(@RequestHeader("Authorization") String token, @RequestBody String newPassword) {
        sellerService.updatePassword(token, newPassword);
        return "Password updated successfully!";
    }

    @PutMapping("/seller/update/mobile")
    public String updateSellerMobile(@RequestHeader("Authorization") String token, @RequestBody String newMobile) {
        sellerService.updateMobile(token, newMobile);
        return "Mobile number updated successfully!";
    }

    @DeleteMapping("/seller/{sellerid}")
    public String deleteSeller(@RequestHeader("Authorization") String token, @PathVariable long sellerid) {
        sellerService.deleteSeller(token, sellerid);
        return "Seller deleted successfully!";
    }
}
