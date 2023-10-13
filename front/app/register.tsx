import {
  Box,
  Center,
  Input,
  InputField,
  InputIcon,
  InputSlot,
  EyeIcon,
  Button,
  ButtonText,
  Text,
  Progress,
  ProgressFilledTrack,
  ButtonIcon,
  ArrowLeftIcon,
  HStack,
  Icon,
  VStack,
} from "@gluestack-ui/themed";
import { router } from "expo-router";
import { UserIcon } from "lucide-react-native";

export default function Register() {
  return (
    <Box h="$full">
      <VStack
        space="2xl"
        style={{
          top: 80,
          alignSelf: "center",
          alignItems: "center",
          display: "flex",
        }}
      >
        <HStack space="xs" alignItems="center">
          <Icon as={UserIcon} size="xl" />
          <Text size="3xl">Create an account</Text>
        </HStack>
        <Progress value={40} w={300} size="md">
          <ProgressFilledTrack />
        </Progress>
      </VStack>
      <HStack h="80%">
        <Box width="80%" gap="$10" alignItems="center">
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

          <Input
            variant="underlined"
            size="md"
            isDisabled={false}
            isInvalid={false}
            isReadOnly={false}
            w="$full"
          >
            <InputField placeholder="Confirm your password" type="password" />
            <InputSlot>
              <InputIcon as={EyeIcon} size="xl" />
            </InputSlot>
          </Input>
        </Box>
      </HStack>

      <HStack
        space="md"
        style={{ bottom: 30, alignSelf: "center", position: "absolute" }}
      >
        <Button
          size="md"
          width="$12"
          variant="solid"
          action="secondary"
          isDisabled={false}
          isFocusVisible={false}
          onPress={() => router.push("/login")}
        >
          <ButtonIcon as={ArrowLeftIcon} />
        </Button>
        <Button
          size="md"
          width="$64"
          variant="solid"
          action="primary"
          isDisabled={false}
          isFocusVisible={false}
        >
          <ButtonText>Continue</ButtonText>
        </Button>
      </HStack>
    </Box>
  );
}
