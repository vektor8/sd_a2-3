import { User } from "../../model/user";

export interface UserState {
  userData: User | null
  isLoggedIn: boolean;
}

export const userInitialState: UserState = {
  isLoggedIn: false,
  userData: null,
};