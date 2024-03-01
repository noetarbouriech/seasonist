import "core-js/stable/atob";
import { jwtDecode } from "jwt-decode";
import { create } from "zustand";

import { Env } from "../constants/Env";
import { RegisterRequest } from "../types/api-requests/register.type";

type State = {
  isLogged: boolean;
  sub: string | null;
  accessToken: string | null;
  refreshToken: string | null;
};

type Action = {
  setTokens: (accessToken: string, refreshToken: string) => void;
  register: (request: RegisterRequest) => Promise<boolean>;
};

export const useAuthStore = create<State & Action>((set, get) => ({
  isLogged: false,
  sub: null,
  accessToken: null,
  refreshToken: null,
  setTokens: (accessToken, refreshToken) =>
    set(() => {
      const decoded = jwtDecode(accessToken);
      return {
        isLogged: true,
        sub: decoded.sub ?? null,
        accessToken,
        refreshToken,
      };
    }),
  register: async (request: RegisterRequest): Promise<boolean> => {
    try {
      const response = await fetch(`${Env.API_URL}/api/auth/register`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(request),
      });

      if (!response.ok) {
        return false;
      }

      return true;
    } catch (e) {
      console.log(e);
      return false;
    }
  },
}));
