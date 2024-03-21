package com.tranhunghoan.Online.Food.Order.controller.admin;

import com.tranhunghoan.Online.Food.Order.model.IngredientsCategory;
import com.tranhunghoan.Online.Food.Order.model.IngredientsItem;
import com.tranhunghoan.Online.Food.Order.model.Restaurant;
import com.tranhunghoan.Online.Food.Order.model.User;
import com.tranhunghoan.Online.Food.Order.request.IngredientsCategoryRequest;
import com.tranhunghoan.Online.Food.Order.request.IngredientsItemRequest;
import com.tranhunghoan.Online.Food.Order.request.RestaurantRequest;
import com.tranhunghoan.Online.Food.Order.service.IngredientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ingredients")
public class AdminIngredientController {

    @Autowired
    private IngredientsService ingredientsService;

    @PostMapping("/category")
    public ResponseEntity<IngredientsCategory> createIngredientsCategory(
            @RequestBody IngredientsCategoryRequest req) throws Exception {
        IngredientsCategory ingredientsCategory =ingredientsService.createIngredientsCategory(req.getName(), req.getRestaurantId());

        return new ResponseEntity<>(ingredientsCategory, HttpStatus.CREATED);
    }
    @PostMapping("/item")
    public ResponseEntity<IngredientsItem> createIngredientsItem(
            @RequestBody IngredientsItemRequest req) throws Exception {
        IngredientsItem ingredientsItem =ingredientsService.createIngredientsItem(req.getRestaurantId(), req.getName(), req.getCategoryId());

        return new ResponseEntity<>(ingredientsItem, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/stoke")
    public ResponseEntity<IngredientsItem> updateIngredientsStock(
            @PathVariable Long id) throws Exception {
        IngredientsItem ingredientsItem =ingredientsService.updateStock(id);
        return new ResponseEntity<>(ingredientsItem, HttpStatus.OK);
    }
    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<IngredientsItem>> getRestaurantIngredient(
            @PathVariable Long id) throws Exception {
        List<IngredientsItem> ingredientsItems =ingredientsService.findRestaurantIngredients(id);

        return new ResponseEntity<>(ingredientsItems, HttpStatus.CREATED);
    }
    @GetMapping("/restaurant/{id}/category")
    public ResponseEntity<List<IngredientsCategory>> getRestaurantIngredientCategory(
            @PathVariable Long id) throws Exception {
        List<IngredientsCategory> ingredientsCategories =ingredientsService.findIngredientsCategoryByRestaurantId(id);

        return new ResponseEntity<>(ingredientsCategories, HttpStatus.CREATED);
    }
}
