package com.tranhunghoan.Online.Food.Order.repository;

import com.tranhunghoan.Online.Food.Order.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {
    @Query("select r from Restaurant r WHERE lower(r.name) ILIKE lower(concat('%', :query, '%')) " +
            "OR lower(r.CuisineType) ILIKE lower(concat('%', :query, '%'))")

    List<Restaurant> findBySearchQuery(String query);

    Restaurant findByOwnerId(Long UserId);
}
