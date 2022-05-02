import { createSlice } from "@reduxjs/toolkit";
import { loginCaseReducer, logoutCaseReducer } from "./reducers";
import { userInitialState } from "./state";

const UserReducerSlice = createSlice({
  name: 'user',
  initialState: userInitialState,
  reducers: {
    login: loginCaseReducer,
    logout: logoutCaseReducer
  },
});

export const { login, logout } = UserReducerSlice.actions;

export const UserReducer = UserReducerSlice.reducer;