package com.tranhunghoan.Online.Food.Order.controller.admin;

import com.tranhunghoan.Online.Food.Order.model.Category;
import com.tranhunghoan.Online.Food.Order.model.Restaurant;
import com.tranhunghoan.Online.Food.Order.model.User;
import com.tranhunghoan.Online.Food.Order.request.RestaurantRequest;
import com.tranhunghoan.Online.Food.Order.service.CategoryService;
import com.tranhunghoan.Online.Food.Order.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/category")
public class AdminCategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<Category> createRestaurant(
            @RequestBody Category req,
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        Category category = categoryService.createCategory(req.getName(), user.getId());
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @GetMapping("/restaurant")
    public ResponseEntity<List<Category>> getRestaurantCategory(
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        List<Category> categories = categoryService.findCategoryByRestaurantId(user.getId());
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
