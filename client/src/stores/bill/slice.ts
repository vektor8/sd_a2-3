import { createSlice } from "@reduxjs/toolkit";
import { addFoodToCartReducer, clearCartReducer, selectCustomerReducer, selectRestaurantReducer } from "./reducers";
import { billInitialState } from "./state";

const BillReducerSlice = createSlice({
  name: 'bill',
  initialState: billInitialState,
  reducers: {
    addFoodToCart: addFoodToCartReducer,
    clearCart: clearCartReducer,
    selectCustomer: selectCustomerReducer,
    selectRestaurant: selectRestaurantReducer
  },
});

export const {addFoodToCart, clearCart, selectCustomer, selectRestaurant} = BillReducerSlice.actions;

export const BillReducer = BillReducerSlice.reducer;