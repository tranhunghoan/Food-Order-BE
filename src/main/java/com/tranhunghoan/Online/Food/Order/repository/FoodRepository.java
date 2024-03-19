package com.tranhunghoan.Online.Food.Order.repository;

import com.tranhunghoan.Online.Food.Order.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food,Long> {
    List<Food> findByRestaurantId(Long restaurantId);

    @Query("select f FROM Food f WHERE f.name like %:keyword% or f.foodCategory.name like %:keyword%")
    List<Food> searchFood(String keyword);

}
