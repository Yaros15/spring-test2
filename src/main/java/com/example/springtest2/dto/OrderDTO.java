package com.example.springtest2.dto;

import java.util.List;

public class OrderDTO {

    private Long customerId;
    private List<Long> productIds;

    public OrderDTO(Long customerId, List<Long> productIds) {
        this.customerId = customerId;
        this.productIds = productIds;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }

}
