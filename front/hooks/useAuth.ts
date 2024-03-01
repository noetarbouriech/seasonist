import {
  CodeChallengeMethod,
  DiscoveryDocument,
  exchangeCodeAsync,
  makeRedirectUri,
  useAuthRequest,
  useAutoDiscovery,
} from "expo-auth-session";
import * as SecureStore from "expo-secure-store";
import { useEffect } from "react";
import { Platform } from "react-native";

import { Env } from "../constants/Env";
import { useAuthStore } from "../stores/auth.store";

const SECURE_AUTH_STATE_KEY = "SecureAuthStateKey";

export function useDiscovery(): DiscoveryDocument | null {
  return useAutoDiscovery(Env.KEYCLOAK_REALM);
}

export function useAuth(discovery: DiscoveryDocument | null) {
  const setTokens = useAuthStore((state) => state.setTokens);
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  const [request, result, promptAsync] = useAuthRequest(
    {
      clientId: Env.KEYCLOAK_CLIENT_ID,
      redirectUri: makeRedirectUri({
        scheme: Env.SCHEMA,
      }),
      scopes: ["openid", "email", "profile"],
      codeChallengeMethod: CodeChallengeMethod.S256,
    },
    discovery,
  );

  // Save the state on storage
  useEffect(() => {
    if (!request || !result || Platform.OS === "web") return;
    if (result.type !== "success" || !request.codeVerifier) return;
    const { codeVerifier } = request;

    try {
      const storageValue = JSON.stringify({
        params: result.params,
        codeVerifier,
      });
      SecureStore.setItemAsync(SECURE_AUTH_STATE_KEY, storageValue).catch(
        console.error,
      );
    } catch (e) {
      console.error(e);
    }
  }, [result, request]);

  // Exchange code for access & refresh tokens
  useEffect(() => {
    if (!request || !result || !discovery) return;
    if (result.type !== "success" || !request.codeVerifier) return;
    const { code } = result.params;
    const { codeVerifier } = request;

    exchangeCodeAsync(
      {
        clientId: Env.KEYCLOAK_CLIENT_ID,
        redirectUri: makeRedirectUri({
          scheme: Env.SCHEMA,
        }),
        scopes: ["openid", "email", "profile"],
        code,
        extraParams: {
          code_verifier: codeVerifier,
        },
      },
      discovery,
    )
      .then((resp) => {
        if (resp.refreshToken === undefined) return;
        setTokens(resp.accessToken, resp.refreshToken);
      })
      .catch(() => ({}));
  }, [result, request]);

  return { request, promptAsync };
}
