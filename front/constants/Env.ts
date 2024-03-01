type EnvType = {
  API_URL: string;
  KEYCLOAK_REALM: string;
  KEYCLOAK_CLIENT_ID: string;
  SCHEMA: string;
};

export const Env: EnvType = {
  API_URL: process.env.EXPO_PUBLIC_API_URL!,
  KEYCLOAK_REALM: process.env.EXPO_PUBLIC_KEYCLOAK_REALM!,
  KEYCLOAK_CLIENT_ID: process.env.EXPO_PUBLIC_KEYCLOAK_CLIENT_ID!,
  SCHEMA: process.env.EXPO_PUBLIC_APP_SCHEMA!,
};
