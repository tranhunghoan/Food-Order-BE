package com.tranhunghoan.Online.Food.Order.request;

import com.tranhunghoan.Online.Food.Order.model.Category;
import com.tranhunghoan.Online.Food.Order.model.IngredientsItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class FoodRequest {

    private String name;
    private String description;
    private Long price;

    private Category category;
    private List<String> images;

    private Long restaurantId;
    private boolean isVegetarian;
    private boolean isSeasonal;
    private List<IngredientsItem> ingredients;



}
