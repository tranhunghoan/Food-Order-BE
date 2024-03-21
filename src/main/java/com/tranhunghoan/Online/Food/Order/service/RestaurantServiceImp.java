package com.tranhunghoan.Online.Food.Order.service;

import com.tranhunghoan.Online.Food.Order.dto.RestaurantDTO;
import com.tranhunghoan.Online.Food.Order.model.Address;
import com.tranhunghoan.Online.Food.Order.model.Restaurant;
import com.tranhunghoan.Online.Food.Order.model.User;
import com.tranhunghoan.Online.Food.Order.repository.AddressRepository;
import com.tranhunghoan.Online.Food.Order.repository.RestaurantRepository;
import com.tranhunghoan.Online.Food.Order.repository.UserRepository;
import com.tranhunghoan.Online.Food.Order.request.RestaurantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImp implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Restaurant createRestaurant(RestaurantRequest req, User user) {

        Address address = addressRepository.save(req.getAddress());
        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(address);
        restaurant.setName(req.getName());
        restaurant.setDescription(req.getDescription());
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setImages(req.getImages());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setOwner(user);
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, RestaurantRequest reqUpdate) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        if (restaurant.getCuisineType() != null) {
            restaurant.setCuisineType(reqUpdate.getCuisineType());
        }
        if (restaurant.getDescription() != null) {
            restaurant.setDescription(reqUpdate.getDescription());
        }
        if (restaurant.getName() != null) {
            restaurant.setName(reqUpdate.getName());
        }
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);

        restaurantRepository.delete(restaurant);

    }

    @Override
    public List<Restaurant> getAllRestaurant() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyword) {
        return restaurantRepository.findBySearchQuery(keyword);
    }

    @Override
    public Restaurant findRestaurantById(Long id) throws Exception {
        Optional<Restaurant> op = restaurantRepository.findById(id);
        if (op.isEmpty()) {
            throw new Exception("restaurant not found with id:" + id);
        }
        return op.get();
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
        Restaurant restaurant = restaurantRepository.findByOwnerId(userId);
        if (restaurant == null) {
            throw new Exception("Owner not found with id:" + userId);
        }

        return restaurant;
    }

    @Override
    public RestaurantDTO addToFavorites(Long restaurantId, User user) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);

        RestaurantDTO restaurantDTO = new RestaurantDTO();
        restaurantDTO.setDescription(restaurant.getDescription());
        restaurantDTO.setImages(restaurant.getImages());
        restaurantDTO.setTitle(restaurant.getName());
        restaurantDTO.setId(restaurantId);

        boolean isFavorites = false;
        List<RestaurantDTO> favorites = user.getFavorites();
        for(RestaurantDTO favorite:favorites){
            if(favorite.getId().equals(restaurantId));{
                isFavorites = true;
                break;
            }
        }
        if(isFavorites){
            favorites.removeIf(favorite -> favorite.getId().equals(restaurantId));
        }else{
            favorites.add(restaurantDTO);
        }
        userRepository.save(user);
        return restaurantDTO;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {
        Restaurant restaurant = findRestaurantById(id);
        restaurant.setOpen(!restaurant.isOpen());

        return restaurantRepository.save(restaurant);
    }
}
