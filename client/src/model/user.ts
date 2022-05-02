import { Order } from "./order"
import { Restaurant } from "./restaurant"

export interface User {
    email: String,
    admin:boolean,
    id: Number,
}

export interface Admin extends User{
    restaurants: [Restaurant]
}

export interface Customer extends User{
    orders : [Order]
}