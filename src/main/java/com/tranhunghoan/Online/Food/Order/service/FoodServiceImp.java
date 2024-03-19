package com.tranhunghoan.Online.Food.Order.service;

import com.tranhunghoan.Online.Food.Order.model.Category;
import com.tranhunghoan.Online.Food.Order.model.Food;
import com.tranhunghoan.Online.Food.Order.model.Restaurant;
import com.tranhunghoan.Online.Food.Order.repository.FoodRepository;
import com.tranhunghoan.Online.Food.Order.request.FoodRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodServiceImp implements FoodService {
    @Autowired
    private FoodRepository foodRepository;

    @Override
    public Food createFood(FoodRequest req, Category category, Restaurant restaurant) {
        Food food = new Food();

        food.setName(req.getName());
        food.setFoodCategory(req.getCategory());
        food.setRestaurant(restaurant);
        food.setFoodCategory(category);
        food.setDescription(req.getDescription());
        food.setPrice(req.getPrice());
        food.setIngredients(req.getIngredients());
        food.setImages(req.getImages());
        food.setSeasonal(req.isSeasonal());
        food.setVegetarian(req.isVegetarian());

        Food saveFood = foodRepository.save(food);
        restaurant.getFoods().add(saveFood);
        return saveFood;
    }

    @Override
    public void deleteFood(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setRestaurant(null);
        foodRepository.save(food);
    }

    @Override
    public List<Food> getRestaurantFood(Long restaurantId,
                                        boolean isVegetarian,
                                        boolean isNonVegetarian,
                                        boolean isSeasonal,
                                        String foodCategory) {

        List<Food> foods = foodRepository.findByRestaurantId(restaurantId);

        if (isVegetarian) {
            foods = filterByVegetarian(foods, isVegetarian);
        }
        if (isNonVegetarian) {
            foods = filterByNonveg(foods, isNonVegetarian);
        }
        if (isSeasonal) {
            foods = filterBySeasonal(foods, isSeasonal);
        }
        if (foodCategory != null && !foodCategory.equals("")) {
            foods = filterByCategory(foods, foodCategory);
        }
        return foods;
    }

    private List<Food> filterByVegetarian(List<Food> foods, boolean isVegetarian) {
        return foods.stream().filter(food -> food.isVegetarian()==isVegetarian).collect(Collectors.toList());
    }
    private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
        return  foods.stream().filter(food -> {
            if(food.getFoodCategory()!=null){
                return food.getFoodCategory().getName().equals(foodCategory);
            }
            return false;
        }).collect(Collectors.toList());
    }

    private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
        return foods.stream().filter(food -> food.isSeasonal()==isSeasonal).collect(Collectors.toList());
    }

    private List<Food> filterByNonveg(List<Food> foods, boolean isNonVegetarian) {
        return foods.stream().filter(food -> food.isVegetarian()==false).collect(Collectors.toList());
    }

    @Override
    public List<Food> searchFood(String keyword) {
        return foodRepository.searchFood(keyword);
    }

    @Override
    public Food findFoodById(Long foodId) throws Exception {
        Optional<Food> optionalFood = foodRepository.findById(foodId);

        if(optionalFood.isEmpty())
        {
            throw new Exception("food not exist ...");
        }
        return optionalFood.get();
    }

    @Override
    public Food updateAvailabilityStatus(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setAvailable(!food.isAvailable());
        return foodRepository.save(food);
    }
}
