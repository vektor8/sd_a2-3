package sd.utcn.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sd.utcn.server.dto.FoodDto;
import sd.utcn.server.dto.NewFoodDto;
import sd.utcn.server.service.FoodService;
import sd.utcn.server.viewmodels.NewFoodViewModel;

@Slf4j
@RestController
@RequestMapping("/api/food")
public class FoodController {

    private final FoodService foodService;

    @Autowired
    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @PostMapping
    public ResponseEntity<FoodDto> addFoodToRestaurant(Authentication authentication, @RequestBody NewFoodViewModel newFood) {
        try {
            log.info("Trying to add new food to restaurant with id: {}", newFood.getRestaurantId());
            var dto = new NewFoodDto(newFood.getName(), newFood.getDescription(), newFood.getPrice(),
                    newFood.getRestaurantId(), newFood.getCategory(), Long.parseLong(authentication.getName()));
            var res = foodService.addFoodToRestaurant(dto);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
