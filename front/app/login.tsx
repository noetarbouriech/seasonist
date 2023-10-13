import {
  Box,
  Center,
  Image,
  Input,
  InputField,
  InputIcon,
  InputSlot,
  EyeIcon,
  Button,
  ButtonText,
} from "@gluestack-ui/themed";

import LoginPattern from "../components/LoginPattern";
import { router } from "expo-router";

export default function Login() {
  return (
    <LoginPattern>
      <Center h="$full">
        <Box width="$64" gap="$10" alignItems="center">
          <Image
            source={require("../assets/images/logo-vertical.png")}
            alt="logo"
          />
          <Input
            variant="underlined"
            size="md"
            isDisabled={false}
            isInvalid={false}
            isReadOnly={false}
            w="$full"
          >
            <InputField placeholder="Email" />
          </Input>

          <Input
            variant="underlined"
            size="md"
            isDisabled={false}
            isInvalid={false}
            isReadOnly={false}
            w="$full"
          >
            <InputField placeholder="Password" type="password" />
            <InputSlot>
              <InputIcon as={EyeIcon} size="xl" />
            </InputSlot>
          </Input>

          <Box gap={12}>
            <Button
              size="md"
              width="$64"
              variant="solid"
              action="primary"
              isDisabled={false}
              isFocusVisible={false}
              onPress={() => router.push("/other")}
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
