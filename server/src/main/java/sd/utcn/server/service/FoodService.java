package sd.utcn.server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sd.utcn.server.dto.FoodDto;
import sd.utcn.server.dto.NewFoodDto;
import sd.utcn.server.mapper.FoodMapper;
import sd.utcn.server.repository.AdminRepository;
import sd.utcn.server.repository.FoodRepository;
import sd.utcn.server.repository.RestaurantRepository;

import javax.transaction.Transactional;

@Slf4j
@Service
public class FoodService {
    private final FoodRepository foodRepository;
    private final RestaurantRepository restaurantRepository;
    private final AdminRepository adminRepository;

    @Autowired
    public FoodService(FoodRepository foodRepository, RestaurantRepository restaurantRepository, AdminRepository adminRepository) {
        this.foodRepository = foodRepository;
        this.restaurantRepository = restaurantRepository;
        this.adminRepository = adminRepository;
    }

    /**
     * Tries to add a new food for a specific restaurant(given in the parameter) and throws an exception
     * if any of the data is invalid
     *
     * @param newFood {   private String name;
     *                private String description;
     *                private Double price;
     *                private Long restaurantId;
     *                private FoodCategory category;
     *                private Long adminId;}
     * @return
     * @throws Exception
     */
    @Transactional
    public FoodDto addFoodToRestaurant(NewFoodDto newFood) throws Exception {
        if (newFood.getPrice() < 0) {
            log.error("Negative price not allowed");
            throw new Exception("Negative price not allowed");
        }

        var restaurant = restaurantRepository.findById(newFood.getRestaurantId());
        if (restaurant.isEmpty()) {
            log.error("Nonexistent restaurant");
            throw new Exception("Nonexistent restaurant");
        }

        var adminOptional = adminRepository.findById(newFood.getAdminId());
        if (adminOptional.isEmpty()) {
            log.error("Admin not found");
            throw new Exception("Admin not found");
        }

        var admin = adminOptional.get();
        if (admin.getRestaurants().stream().noneMatch(e -> e.getId().equals(newFood.getRestaurantId()))) {
            log.error("Not the admin for this restaurant");
            throw new Exception("You're not the admin of this restaurant");
        }

        var entity = FoodMapper.ToEntity(newFood);
        entity.setRestaurant(restaurant.get());
        var added = foodRepository.save(entity);
        restaurant.get().addFood(added);
        return FoodMapper.ToDto(added);
    }
}
