package com.tranhunghoan.Online.Food.Order.service;

import com.tranhunghoan.Online.Food.Order.model.IngredientsCategory;
import com.tranhunghoan.Online.Food.Order.model.IngredientsItem;
import com.tranhunghoan.Online.Food.Order.model.Restaurant;
import com.tranhunghoan.Online.Food.Order.repository.IngredientCategoryRepository;
import com.tranhunghoan.Online.Food.Order.repository.IngredientItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientsServiceImp implements IngredientsService{
    @Autowired
    private IngredientCategoryRepository ingredientCategoryRepository;

    @Autowired
    private IngredientItemRepository ingredientItemRepository;

    @Autowired
    private RestaurantService restaurantService;
    @Override
    public IngredientsCategory createIngredientsCategory(String name, Long restaurantId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientsCategory ingredientsCategory = new IngredientsCategory();
        ingredientsCategory.setName(name);
        ingredientsCategory.setRestaurant(restaurant);

        return ingredientCategoryRepository.save(ingredientsCategory);
    }

    @Override
    public IngredientsCategory findIngredientsCategoryById(Long id) throws Exception {
        Optional<IngredientsCategory> optionalIngredientsCategory = ingredientCategoryRepository.findById(id);
        if (optionalIngredientsCategory.isEmpty())
        {
            throw new Exception("Ingredient Category not found with id"+id);
        }
        return optionalIngredientsCategory.get();
    }

    @Override
    public List<IngredientsCategory> findIngredientsCategoryByRestaurantId(Long restaurantId) throws Exception {
        restaurantService.findRestaurantById(restaurantId);
        return ingredientCategoryRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItem createIngredientsItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientsCategory ingredientsCategory = findIngredientsCategoryById(categoryId);

        IngredientsItem ingredientsItem = new IngredientsItem();
        ingredientsItem.setName(ingredientName);
        ingredientsItem.setRestaurant(restaurant);
        ingredientsItem.setCategory(ingredientsCategory);

        IngredientsItem ingredient = ingredientItemRepository.save(ingredientsItem);
        ingredientsCategory.getIngredients().add(ingredient);
        return ingredient;
    }

    @Override
    public List<IngredientsItem> findRestaurantIngredients(Long restaurantId) throws Exception {

        return ingredientItemRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItem updateStock(Long id) throws Exception {
        Optional<IngredientsItem> opingredientsItem = ingredientItemRepository.findById(id);
        if(opingredientsItem.isEmpty())
        {
            throw new Exception("Ingredients Item not found");
        }
            IngredientsItem ingredientsItem = new IngredientsItem();
            ingredientsItem.setInStoke(!ingredientsItem.isInStoke());
        return ingredientItemRepository.save(ingredientsItem);
    }
}
