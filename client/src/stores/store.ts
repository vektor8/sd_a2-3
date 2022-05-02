import { combineReducers, configureStore, Reducer } from "@reduxjs/toolkit";
import thunk from "redux-thunk";
import { BillReducer } from "./bill/slice";
import { BillState } from "./bill/state";
import { UserReducer } from "./user/slice";
import { UserState } from "./user/state";
import { persistStore, persistReducer } from 'redux-persist';
import storage from 'redux-persist/lib/storage';

export interface RootState {
  user: UserState;
  bill: BillState;
}

const userPersistConfig = {
  key: 'user',
  storage,
};

const billPersistConfig = {
  key: 'bill',
  storage,
};

const store = configureStore({
  reducer: combineReducers<RootState>({
    user: persistReducer(userPersistConfig, UserReducer as Reducer),
    bill: persistReducer(billPersistConfig, BillReducer as Reducer),
  }),
  middleware: [thunk],
});

const persistor = persistStore(store);

export { store, persistor };
