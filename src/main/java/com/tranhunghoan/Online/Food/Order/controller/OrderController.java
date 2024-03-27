package com.tranhunghoan.Online.Food.Order.controller;

import com.tranhunghoan.Online.Food.Order.model.CartItem;
import com.tranhunghoan.Online.Food.Order.model.Order;
import com.tranhunghoan.Online.Food.Order.model.User;
import com.tranhunghoan.Online.Food.Order.request.AddCartItemRequest;
import com.tranhunghoan.Online.Food.Order.request.OrderRequest;
import com.tranhunghoan.Online.Food.Order.service.OrderService;
import com.tranhunghoan.Online.Food.Order.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping("/order")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest request,
                                             @RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.findUserByJwtToken(jwt);
        Order order = orderService.createOder(request,user);

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping("/order/user")
    public ResponseEntity<List<Order>> getOrderHistory(
                                             @RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.findUserByJwtToken(jwt);
        List<Order> orders = orderService.getOderByUserId(user);

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
