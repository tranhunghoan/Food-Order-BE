package com.tranhunghoan.Online.Food.Order.controller;

import com.tranhunghoan.Online.Food.Order.model.Cart;
import com.tranhunghoan.Online.Food.Order.model.CartItem;
import com.tranhunghoan.Online.Food.Order.request.AddCartItemRequest;
import com.tranhunghoan.Online.Food.Order.request.UpdateCartItemRequest;
import com.tranhunghoan.Online.Food.Order.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartController {
    @Autowired
    private CartService cartService;

    @PutMapping("/cart/add")
    public ResponseEntity<CartItem> addCartItem(@RequestBody AddCartItemRequest request,
                                                @RequestHeader("Authorization") String jwt) throws Exception{
       CartItem cartItem = cartService.addItemToCart(request,jwt);

        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }
    @PutMapping("/cart-item/update")
    public ResponseEntity<CartItem> updateCartItem(@RequestBody UpdateCartItemRequest request,
                                                @RequestHeader("Authorization") String jwt) throws Exception{
        CartItem cartItem = cartService.updateCartItemQuantity(request.getCartItemId(), request.getQuantity());

        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @DeleteMapping("/cart-item/{id}/delete")
    public ResponseEntity<Cart> deleteCartItem(@RequestParam Long id,
                                                @RequestHeader("Authorization") String jwt) throws Exception{
        Cart cart = cartService.removeItemFormCart(id,jwt);

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PutMapping("/cart/clear")
    public ResponseEntity<Cart> clearCart(@RequestHeader("Authorization") String jwt) throws Exception{
        Cart cart = cartService.clearCart(jwt);

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
    @GetMapping("/cart")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws Exception{
        Cart cart = cartService.findCartByUserId(jwt);

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

}
