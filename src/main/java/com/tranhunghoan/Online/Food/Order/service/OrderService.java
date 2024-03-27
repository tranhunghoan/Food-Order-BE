package com.tranhunghoan.Online.Food.Order.service;

import com.tranhunghoan.Online.Food.Order.model.Order;
import com.tranhunghoan.Online.Food.Order.model.User;
import com.tranhunghoan.Online.Food.Order.request.OrderRequest;

import java.util.List;

public interface OrderService {

    public Order createOder(OrderRequest req, User user) throws Exception;
    public Order updateOrder(Long orderId, String orderStatus) throws Exception;
    public void cancelOrder (Long orderId) throws Exception;
    public List<Order> getOderByUserId(User user) throws Exception;
    public List<Order> getRestaurantOrder(Long restaurantId,String orderStatus)throws Exception;
    public Order findOrderById(Long orderId) throws Exception;
}
