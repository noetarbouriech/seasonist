import {
  Box,
  Input,
  InputField,
  InputIcon,
  InputSlot,
  EyeIcon,
  Button,
  ButtonText,
  Text,
  ButtonIcon,
  ArrowLeftIcon,
  HStack,
  Icon,
  VStack,
  KeyboardAvoidingView,
  useToast,
  Toast,
  ToastTitle,
} from "@gluestack-ui/themed";
import { router } from "expo-router";
import { Formik } from "formik";
import { UserIcon } from "lucide-react-native";
import { FormEvent, useCallback, useEffect } from "react";
import { object, ref, string } from "yup";

import { useAuthStore } from "../stores/auth.store";
import { RegisterRequest } from "../types/api-requests/register.type";

const registerSchema = object({
  firstname: string().required(),
  lastname: string().required(),
  email: string().email().required(),
  password: string().min(8).required(),
  confirmPassword: string().oneOf(
    [ref("password"), undefined],
    "Passwords must match",
  ),
});

export default function Register() {
  const toast = useToast();
  const register = useAuthStore((state) => state.register);
  const isLogged = useAuthStore((state) => state.isLogged);

  useEffect(() => {
    if (!isLogged) return;
    router.replace("/home");
  }, [isLogged]);

  const onSubmit = useCallback((values: RegisterRequest) => {
    register(values).then((isRegistered) => {
      if (isRegistered) {
        toast.show({
          placement: "bottom right",
          render: ({ id }) => (
            <Toast nativeID={`toast-${id}`} action="success" variant="accent">
              <VStack space="xs">
                <ToastTitle>Account successfully created</ToastTitle>
              </VStack>
            </Toast>
          ),
        });
        router.replace("/login");
      }
    });
  }, []);

  return (
    <KeyboardAvoidingView>
      <VStack
        space="2xl"
        h="$full"
        style={{
          paddingTop: 80,
          paddingBottom: 60,
          alignSelf: "center",
          alignItems: "center",
          display: "flex",
        }}
      >
        <HStack space="xs" alignItems="center">
          <Icon as={UserIcon} size="xl" />
          <Text size="3xl">Create an account</Text>
        </HStack>

        <Formik
          validationSchema={registerSchema}
          initialValues={{
            firstname: "",
            lastname: "",
            email: "",
            password: "",
            confirmPassword: "",
          }}
          onSubmit={onSubmit}
        >
          {({ handleChange, handleBlur, handleSubmit, values, errors }) => (
            <VStack h="$full">
              <Box width="80%" gap="$10" alignItems="center">
                {/* firstname */}
                <Input
                  variant="underlined"
                  size="md"
                  isInvalid={errors.firstname !== undefined}
                  w="$full"
                >
                  <InputField
                    placeholder="Firstname"
                    value={values.firstname}
                    onChangeText={handleChange("firstname")}
                    onBlur={handleBlur("firstname")}
                  />
                </Input>
                {errors.firstname && (
                  <Text color="$red" size="sm">
                    {errors.firstname}
                  </Text>
                )}

                {/* family name */}
                <Input
                  variant="underlined"
                  size="md"
                  isInvalid={errors.lastname !== undefined}
                  w="$full"
                >
                  <InputField
                    placeholder="Family name"
                    value={values.lastname}
                    onChangeText={handleChange("lastname")}
                    onBlur={handleBlur("lastname")}
                  />
                </Input>
                {errors.lastname && (
                  <Text color="$red" size="sm">
                    {errors.confirmPassword}
                  </Text>
                )}

                {/* email */}
                <Input
                  variant="underlined"
                  size="md"
                  isInvalid={errors.email !== undefined}
                  w="$full"
                >
                  <InputField
                    placeholder="Email"
                    value={values.email}
                    onChangeText={handleChange("email")}
                    onBlur={handleBlur("email")}
                  />
                </Input>
                {errors.email && (
                  <Text color="$red" size="sm">
                    {errors.email}
                  </Text>
                )}

                {/* password */}
                <Input
                  variant="underlined"
                  size="md"
                  isInvalid={errors.password !== undefined}
                  w="$full"
                >
                  <InputField
                    placeholder="Password"
                    type="password"
                    value={values.password}
                    onChangeText={handleChange("password")}
                    onBlur={handleBlur("password")}
                  />
                  <InputSlot>
                    <InputIcon as={EyeIcon} size="xl" />
                  </InputSlot>
                </Input>

                <Input
                  variant="underlined"
                  size="md"
                  isInvalid={errors.confirmPassword !== undefined}
                  w="$full"
                >
                  <InputField
                    placeholder="Confirm your password"
                    type="password"
                    value={values.confirmPassword}
                    onChangeText={handleChange("confirmPassword")}
                    onBlur={handleBlur("confirmPassword")}
                  />
                  <InputSlot>
                    <InputIcon as={EyeIcon} size="xl" />
                  </InputSlot>
                </Input>

                {errors.confirmPassword && (
                  <Text color="$red" size="sm">
                    {errors.confirmPassword}
                  </Text>
                )}
              </Box>

              <HStack
                space="md"
                style={{
                  bottom: 30,
                  alignSelf: "center",
                  position: "absolute",
                }}
              >
                <Button
                  size="md"
                  width="$12"
                  variant="solid"
                  action="secondary"
                  onPress={() => router.push("/login")}
                >
                  <ButtonIcon as={ArrowLeftIcon} />
                </Button>
                <Button
                  size="md"
                  width="$64"
                  variant="solid"
                  action="primary"
                  onPress={(e) =>
                    handleSubmit(e as unknown as FormEvent<HTMLFormElement>)
                  }
                >
                  <ButtonText>Register</ButtonText>
                </Button>
              </HStack>
            </VStack>
          )}
        </Formik>
      </VStack>
    </KeyboardAvoidingView>
  );
}
