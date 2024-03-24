package com.tranhunghoan.Online.Food.Order.service;

import com.tranhunghoan.Online.Food.Order.model.Cart;
import com.tranhunghoan.Online.Food.Order.model.CartItem;
import com.tranhunghoan.Online.Food.Order.model.Food;
import com.tranhunghoan.Online.Food.Order.model.User;
import com.tranhunghoan.Online.Food.Order.repository.CartItemRepository;
import com.tranhunghoan.Online.Food.Order.repository.CartRepository;
import com.tranhunghoan.Online.Food.Order.repository.FoodRepository;
import com.tranhunghoan.Online.Food.Order.request.AddCartItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImp implements CartService{
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private FoodService foodService;

    @Override
    public CartItem addItemToCart(AddCartItemRequest req, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        Food food = foodService.findFoodById(req.getFoodId());

        Cart cart = cartRepository.findByCustomId(user.getId());

        for (CartItem cartItem:cart.getItem())
        {
            if (cartItem.getFood().equals(food)){
                int newQuantity = cartItem.getQuantity() + req.getQuantity();
                return updateCartItemQuantity(cartItem.getId(),newQuantity);
            }
        }

        CartItem cartItem = new CartItem();
        cartItem.setFood(food);
        cartItem.setCart(cart);
        cartItem.setQuantity(req.getQuantity());
        cartItem.setIngredients(req.getIngredients());
        cartItem.setTotalPrice(req.getQuantity()* food.getPrice());

        CartItem saveCartItem = cartItemRepository.save(cartItem);
        cart.getItem().add(saveCartItem);

        return saveCartItem;
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
        Optional<CartItem> opCartItem = cartItemRepository.findById(cartItemId);

        if(opCartItem.isEmpty()){
            throw new Exception("Cart item is not exist");
        }
        CartItem cartItem = opCartItem.get();
        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice(cartItem.getFood().getPrice()*quantity);
        return cartItemRepository.save(cartItem);
    }

    @Override
    public Cart removeItemFormCart(Long cartItemId, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        Cart cart = cartRepository.findByCustomId(user.getId());

        Optional<CartItem> opCartItem = cartItemRepository.findById(cartItemId);

        if(opCartItem.isEmpty()){
            throw new Exception("Cart item is not exist");
        }
        CartItem cartItem = opCartItem.get();
        cart.getItem().remove(cartItem);

        return cartRepository.save(cart);
    }

    @Override
    public Long calculateCartTotals(Cart cart) throws Exception {
        Long total = 0L;
        for (CartItem cartItem:cart.getItem())
        {
            total += cartItem.getFood().getPrice()*cartItem.getQuantity();
        }
        return total;
    }

    @Override
    public Cart findCartById(Long id) throws Exception {
        Optional<Cart> opCart = cartRepository.findById(id);

        if(opCart.isEmpty()){
            throw new Exception("Cart is exist");
        }
        return opCart.get();
    }

    @Override
    public Cart findCartByUserId(String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        return cartRepository.findByCustomId(user.getId());
    }

    @Override
    public Cart clearCart(String jwt) throws Exception {
        Cart cart = findCartByUserId(jwt);

        cart.getItem().clear();
        return cartRepository.save(cart);
    }
}
