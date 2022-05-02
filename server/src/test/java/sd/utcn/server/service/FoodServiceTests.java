package sd.utcn.server.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import sd.utcn.server.dto.NewFoodDto;
import sd.utcn.server.model.Admin;
import sd.utcn.server.model.Food;
import sd.utcn.server.model.Restaurant;
import sd.utcn.server.repository.AdminRepository;
import sd.utcn.server.repository.FoodRepository;
import sd.utcn.server.repository.RestaurantRepository;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FoodServiceTests {
    @MockBean
    private final FoodRepository foodRepository;
    @MockBean
    private final RestaurantRepository restaurantRepository;
    @MockBean
    private final AdminRepository adminRepository;

    private final FoodService foodService;

    @Autowired
    public FoodServiceTests(FoodRepository foodRepository, RestaurantRepository restaurantRepository, AdminRepository adminRepository, FoodService foodService) {
        this.foodRepository = foodRepository;
        this.restaurantRepository = restaurantRepository;
        this.adminRepository = adminRepository;
        this.foodService = foodService;
    }

    @Test
    public void addFoodToRestaurantTest() throws Exception {
        var dto = NewFoodDto.builder()
                .name("pizza")
                .adminId(17L)
                .description("abcd")
                .price(20.0)
                .restaurantId(19L)
                .build();
        var admin = Admin.builder().id(17L).build();
        var restaurant = Restaurant.builder().id(19L)
                .foods(new ArrayList<>()).build();
        admin.setRestaurants(new ArrayList<>());
        admin.addRestaurant(restaurant);

        var entity = Food.builder()
                .id(10L)
                .name("pizza")
                .description("abcd")
                .price(20.0)
                .build();
        Mockito.when(adminRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(admin));
        Mockito.when(restaurantRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(restaurant));
        Mockito.when(foodRepository.save(Mockito.any())).thenReturn(entity);
        var res = foodService.addFoodToRestaurant(dto);
        assert res.getName().equals(dto.getName());
        assert res.getPrice().equals(dto.getPrice());
    }

    @Test
    public void addFoodToRestaurantTestNegativePrice() throws Exception {
        var dto = NewFoodDto.builder()
                .name("pizza")
                .adminId(17L)
                .description("abcd")
                .price(-20.0)
                .restaurantId(19L)
                .build();
        var admin = Admin.builder().id(17L).build();
        var restaurant = Restaurant.builder().id(19L)
                .foods(new ArrayList<>()).build();
        admin.setRestaurants(new ArrayList<>());
        admin.addRestaurant(restaurant);
        Exception exception = assertThrows(Exception.class, () -> foodService.addFoodToRestaurant(dto));
        assert exception.getMessage().equals("Negative price not allowed");
    }
}
