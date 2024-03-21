package com.tranhunghoan.Online.Food.Order.service;

import com.tranhunghoan.Online.Food.Order.model.IngredientsCategory;
import com.tranhunghoan.Online.Food.Order.model.IngredientsItem;

import java.util.List;

public interface IngredientsService {
    public IngredientsCategory createIngredientsCategory (String name,Long restaurantId) throws Exception;

    public IngredientsCategory findIngredientsCategoryById(Long id) throws Exception;

    public List<IngredientsCategory>  findIngredientsCategoryByRestaurantId (Long restaurantId) throws Exception;

    public IngredientsItem createIngredientsItem (Long restaurantId,String ingredientName,Long categoryId) throws Exception;

    public List<IngredientsItem> findRestaurantIngredients (Long restaurantId) throws Exception;

    public IngredientsItem updateStock(Long id) throws Exception;
}
