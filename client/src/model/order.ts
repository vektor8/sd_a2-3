import { Food } from "./food";

export interface Order {
    id: number,
    orderStatus: string,
    orderedFoods: [OrderedFood]
}

export interface OrderedFood {
    id: number,
    food: Food,
    quantity: number
}

export interface FoodToOrder {
    foodId : number,
    quantity: number
}