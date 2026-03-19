package com.ecommerce.rest.Dto;

public class CartDeleteRequest {
    private long itemId;
    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }
}
