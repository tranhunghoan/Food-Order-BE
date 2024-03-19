package com.tranhunghoan.Online.Food.Order.controller;

import com.tranhunghoan.Online.Food.Order.model.Food;
import com.tranhunghoan.Online.Food.Order.model.Restaurant;
import com.tranhunghoan.Online.Food.Order.model.User;
import com.tranhunghoan.Online.Food.Order.request.FoodRequest;
import com.tranhunghoan.Online.Food.Order.service.FoodService;
import com.tranhunghoan.Online.Food.Order.service.RestaurantService;
import com.tranhunghoan.Online.Food.Order.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {
    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/search")
    public ResponseEntity<List<Food> > searchFood(
            @RequestParam String keyword,
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Food> foods = foodService.searchFood(keyword);
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    @GetMapping("restaurant/{restaurantId}")
    public ResponseEntity<List<Food> > searchFood(
           @RequestParam boolean vegetarian,
           @RequestParam boolean seasonal,
           @RequestParam boolean nonveg,
           @RequestParam(required = false) String food_category,
           @PathVariable Long restaurantId,
           @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Food> foods = foodService.getRestaurantFood(restaurantId,vegetarian,nonveg,seasonal,food_category);
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }


}
