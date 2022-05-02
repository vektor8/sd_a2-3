import { PayloadAction } from "@reduxjs/toolkit";
import { FoodToOrder } from "../../model/order";
import { BillState } from "./state";

export const addFoodToCartReducer = (
    state: BillState, action: PayloadAction<FoodToOrder>
) => {
    const food = action.payload;
    console.log(food);
    const updateItem = state.orderedFoods.find(e => e.foodId == food.foodId);
    if (!updateItem) {
        state.orderedFoods.push(food);
    } else {
        const index = state.orderedFoods.indexOf(updateItem);
        state.orderedFoods[index] = food;
    }
    state.orderedFoods = state.orderedFoods.filter(e => e.quantity > 0) as unknown as [FoodToOrder]
    console.log(JSON.parse(JSON.stringify(state.orderedFoods)));
};

export const clearCartReducer = (
    state: BillState) => {
    state.orderedFoods = [] as unknown as [FoodToOrder];
};

export const selectCustomerReducer = (
    state: BillState, action: PayloadAction<Number>
) => {
    state.customerId = action.payload;
};

export const selectRestaurantReducer = (
    state: BillState, action: PayloadAction<Number>
) => {
    state.restaurantId = action.payload;
    state.orderedFoods = [] as unknown as [FoodToOrder];
};