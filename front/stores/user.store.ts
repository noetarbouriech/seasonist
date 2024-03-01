import { create } from "zustand";

import { Env } from "../constants/Env";
import { User } from "../types/users/user.type";

type State = object;

type Action = {
  getUser: (userId: string) => Promise<User>;
};

export const useUserStore = create<State & Action>(() => ({
  getUser: async (userId) => {
    const res = await fetch(`${Env.API_URL}/api/users/${userId}`);

    return ((await res.json()) as any).data.userById as User;
  },
}));
