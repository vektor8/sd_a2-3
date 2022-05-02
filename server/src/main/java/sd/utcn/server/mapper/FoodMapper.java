package sd.utcn.server.mapper;

import sd.utcn.server.dto.FoodDto;
import sd.utcn.server.dto.NewFoodDto;
import sd.utcn.server.model.Food;

public class FoodMapper {

    public static FoodDto ToDto(Food food){
        var f = new FoodDto();
        f.setDescription(food.getDescription());
        f.setName(food.getName());
        f.setId(food.getId());
        f.setPrice(food.getPrice());
        f.setCategory(food.getCategory());
        return f;
    }

    public static Food ToEntity(NewFoodDto newFood){
        var f = new Food();
        f.setDescription(newFood.getDescription());
        f.setName(newFood.getName());
        f.setPrice(newFood.getPrice());
        f.setCategory(newFood.getCategory());
        return f;
    }
}
