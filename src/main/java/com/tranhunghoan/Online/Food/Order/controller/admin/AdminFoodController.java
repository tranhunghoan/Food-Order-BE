package com.tranhunghoan.Online.Food.Order.controller.admin;

import com.tranhunghoan.Online.Food.Order.model.Food;
import com.tranhunghoan.Online.Food.Order.model.Restaurant;
import com.tranhunghoan.Online.Food.Order.model.User;
import com.tranhunghoan.Online.Food.Order.request.FoodRequest;
import com.tranhunghoan.Online.Food.Order.request.RestaurantRequest;
import com.tranhunghoan.Online.Food.Order.response.MessageReponse;
import com.tranhunghoan.Online.Food.Order.service.FoodService;
import com.tranhunghoan.Online.Food.Order.service.RestaurantService;
import com.tranhunghoan.Online.Food.Order.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<Food> createFood(
            @RequestBody FoodRequest req,
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = new Restaurant();
        Food food = foodService.createFood(req, req.getCategory(),restaurant);
        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }
    @DeleteMapping ("/{id}")
    public ResponseEntity<MessageReponse> deleteFood(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        foodService.deleteFood(id);
        MessageReponse mes = new MessageReponse();
        mes.setMessage("restaurant deleted successfully");
        return new ResponseEntity<>(mes, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Food> updateFoodAvailabilityStatus(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Food food = foodService.updateAvailabilityStatus(id);
        return new ResponseEntity<>(food, HttpStatus.OK);
    }
}
