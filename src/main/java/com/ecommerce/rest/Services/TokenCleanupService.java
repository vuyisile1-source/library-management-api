package com.ecommerce.rest.Services;

import com.ecommerce.rest.Dao.CustomerDao;
import com.ecommerce.rest.Dao.SellerDao;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class TokenCleanupService {

    private final CustomerDao customerDao;
    private final SellerDao sellerDao;

    public TokenCleanupService(CustomerDao customerDao, SellerDao sellerDao) {
        this.customerDao = customerDao;
        this.sellerDao = sellerDao;
    }

    @Scheduled(fixedRate = 3600000)
    public void cleanupExpiredTokens() {
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);

        // Clear expired tokens for customers
        customerDao.findAll().forEach(customer -> {
            if (customer.getLastLoginTime() != null && customer.getLastLoginTime().isBefore(oneHourAgo)) {
                customer.setSessionToken(null);
                customer.setLastLoginTime(null);
                customerDao.save(customer);
            }
        });

        sellerDao.findAll().forEach(seller -> {
            if (seller.getLastLoginTime() != null && seller.getLastLoginTime().isBefore(oneHourAgo)) {
                seller.setSessionToken(null);
                seller.setLastLoginTime(null);
                sellerDao.save(seller);
            }
        });
    }
}
