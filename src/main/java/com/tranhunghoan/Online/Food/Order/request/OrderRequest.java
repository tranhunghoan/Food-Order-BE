package com.tranhunghoan.Online.Food.Order.request;

import com.tranhunghoan.Online.Food.Order.model.Address;
import lombok.Data;

@Data
public class OrderRequest {
    private Long restaurantId;
    private Address deliveryAddress;
}
