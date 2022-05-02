import { Food } from "./food"
import { Order } from "./order"



export interface Restaurant {
    id: Number,
    name: string,
    location: string
    foods: [Food]
    orders: [Order]
}

export interface Restaurant2 {
    id: Number,
    name: string,
    location: string
    foods: [Food]
}