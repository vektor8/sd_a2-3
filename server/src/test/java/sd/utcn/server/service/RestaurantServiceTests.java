package sd.utcn.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import sd.utcn.server.dto.NewRestaurantDto;
import sd.utcn.server.model.Admin;
import sd.utcn.server.model.Restaurant;
import sd.utcn.server.repository.AdminRepository;
import sd.utcn.server.repository.RestaurantRepository;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestaurantServiceTests {
    @MockBean
    private final RestaurantRepository restaurantRepository;
    @MockBean
    private final AdminRepository adminRepository;
    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantServiceTests(RestaurantRepository restaurantRepository, AdminRepository adminRepository, RestaurantService restaurantService) {
        this.restaurantRepository = restaurantRepository;
        this.adminRepository = adminRepository;
        this.restaurantService = restaurantService;
    }

    @Test
    public void addRestaurantTest() {
        var admin = Admin.builder()
                .id(10L)
                .restaurants(new ArrayList<>())
                .build();
        var dto = NewRestaurantDto.builder()
                .adminId(10L)
                .location("1231")
                .name("1234")
                .build();
        Mockito.when(adminRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(admin));
        var restaurant = Restaurant.builder()
                .id(1L)
                .orders(new ArrayList<>())
                .location("1231")
                .name("1234")
                .admin(admin)
                .build();
        Mockito.when(restaurantRepository.save(Mockito.any())).thenReturn(restaurant);
        assertDoesNotThrow(() -> restaurantService.add(dto));
    }
}
