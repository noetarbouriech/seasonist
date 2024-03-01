import "core-js/stable/atob";
import { jwtDecode } from "jwt-decode";
import { create } from "zustand";

type State = {
  isLogged: boolean;
  sub: string | null;
  accessToken: string | null;
  refreshToken: string | null;
};

type Action = {
  setTokens: (accessToken: string, refreshToken: string) => void;
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
}));
