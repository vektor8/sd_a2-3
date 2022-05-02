import { FoodToOrder, OrderedFood } from "../../model/order";

export interface BillState {
    customerId: Number,
    restaurantId: Number,
    orderedFoods: [FoodToOrder]
}

export const billInitialState: BillState = {
    customerId: -1,
    restaurantId: -1,
    orderedFoods: [] as unknown as [FoodToOrder]
};