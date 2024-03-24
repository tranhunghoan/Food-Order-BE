package com.tranhunghoan.Online.Food.Order.repository;

import com.tranhunghoan.Online.Food.Order.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
    public Cart findByCustomId(Long userId);
}
