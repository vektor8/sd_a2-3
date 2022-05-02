import { PayloadAction } from "@reduxjs/toolkit";
import { User } from "../../model/user";
import { UserState } from "./state";

export const loginCaseReducer = (
  state: UserState, action: PayloadAction<User>
) => {
  state.isLoggedIn = true;
  state.userData = action.payload;
};

export const logoutCaseReducer = (state: UserState) => {
  state.userData = null;
  state.isLoggedIn = false;
  localStorage.removeItem("access_token");
  localStorage.removeItem("refresh_token");
};
