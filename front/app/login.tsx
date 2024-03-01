import { Box, Center, Image, Button, ButtonText } from "@gluestack-ui/themed";
import { router } from "expo-router";
import * as WebBrowser from "expo-web-browser";
import { useCallback, useEffect } from "react";

import LoginPattern from "../components/LoginPattern";
import { useAuth, useDiscovery } from "../hooks/useAuth";
import { useAuthStore } from "../stores/auth.store";

WebBrowser.maybeCompleteAuthSession();

export default function Login() {
  const discovery = useDiscovery();
  const { request, promptAsync } = useAuth(discovery);

  const isLogged = useAuthStore((state) => state.isLogged);
  useEffect(() => {
    if (!isLogged) return;
    router.replace("/home");
  }, [isLogged]);

  const onLoginClick = useCallback(() => {
    if (request === null) return;
    promptAsync();
  }, [request, promptAsync]);

  return (
    <LoginPattern>
      <Center h="$full">
        <Box width="$64" gap="$10" alignItems="center">
          <Image
            source={require("../assets/images/logo-vertical.png")}
            alt="logo"
          />

          <Box gap={12}>
            <Button
              size="md"
              width="$64"
              variant="solid"
              action="primary"
              isDisabled={false}
              isFocusVisible={false}
              onPress={onLoginClick}
            >
              <ButtonText>Login</ButtonText>
            </Button>
            <Button
              size="md"
              width="$64"
              variant="link"
              action="primary"
              isDisabled={false}
              isFocusVisible={false}
              onPress={() => router.push("/register")}
            >
              <ButtonText>No account? Click here to register.</ButtonText>
            </Button>
          </Box>
        </Box>
      </Center>
    </LoginPattern>
  );
}
