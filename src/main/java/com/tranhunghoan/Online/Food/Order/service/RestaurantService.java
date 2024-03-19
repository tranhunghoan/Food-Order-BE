package com.tranhunghoan.Online.Food.Order.service;

import com.tranhunghoan.Online.Food.Order.dto.RestaurantDTO;
import com.tranhunghoan.Online.Food.Order.model.Restaurant;
import com.tranhunghoan.Online.Food.Order.model.User;
import com.tranhunghoan.Online.Food.Order.request.RestaurantRequest;

import java.util.List;

public interface RestaurantService {
    public Restaurant createRestaurant(RestaurantRequest req, User user);

    public Restaurant updateRestaurant (Long restaurantId,RestaurantRequest reqUpdate) throws Exception;

    public void deleteRestaurant(Long restaurantId) throws Exception;

    public List<Restaurant> getAllRestaurant();

    public List<Restaurant> searchRestaurant(String keyword);

    public Restaurant findRestaurantById(Long Id) throws Exception;

    public Restaurant getRestaurantById(Long Id) throws Exception;

    public RestaurantDTO addToFavorites(Long restaurantId, User user) throws Exception;

    public Restaurant updateRestaurantStatus(Long Id) throws Exception;
}
