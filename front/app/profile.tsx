import { Heading, View, Text, Divider, HStack } from "@gluestack-ui/themed";
import { router } from "expo-router";
import { useEffect, useState } from "react";

import { useAuthStore } from "../stores/auth.store";
import { useUserStore } from "../stores/user.store";
import { genderToString } from "../types/users/gender.type";
import { nationalityToString } from "../types/users/nationality.type";
import { User } from "../types/users/user.type";

export default function Profile() {
  const isLogged = useAuthStore((state) => state.isLogged);
  const userId = useAuthStore((state) => state.sub);
  const getUser = useUserStore((state) => state.getUser);

  const [user, setUser] = useState<User | null>(null);

  useEffect(() => {
    if (isLogged) return;
    router.replace("/login");
  }, [isLogged]);

  useEffect(() => {
    if (userId === null) return;
    getUser(userId).then((user) => setUser(user));
  }, [userId]);

  return (
    <View padding="$4" paddingTop="$16" gap="$4">
      <Heading size="2xl">Profile</Heading>
      <Divider />
      {user && (
        <>
          <HStack gap="$2">
            <Text>First name:</Text>
            <Text fontWeight="$bold">{user.firstname}</Text>
          </HStack>
          <HStack gap="$2">
            <Text>Family name:</Text>
            <Text fontWeight="$bold">{user.lastname}</Text>
          </HStack>
          <HStack gap="$2">
            <Text>Bio:</Text>
            <Text fontWeight="$bold">
              {user.bio ? user.bio : "You do not have any bio yet."}
            </Text>
          </HStack>
          <Divider />
          <HStack gap="$2">
            <Text>Email:</Text>
            <Text fontWeight="$bold">{user.email}</Text>
          </HStack>
          <HStack gap="$2">
            <Text>Phone:</Text>
            <Text fontWeight="$bold">
              {user.phone ? user.phone : "You do not have any\nphone number."}
            </Text>
          </HStack>
          <HStack gap="$2">
            <Text>Address:</Text>
            <Text fontWeight="$bold">
              {user.address
                ? user.address
                : "You do not have registered\nyour addresss."}
            </Text>
          </HStack>
          <Divider />
          <HStack gap="$2">
            <Text>Genre:</Text>
            <Text fontWeight="$bold">{genderToString(user.gender)}</Text>
          </HStack>
          <HStack gap="$2">
            <Text>Nationality:</Text>
            <Text fontWeight="$bold">
              {nationalityToString(user.nationality ?? "OTHER")}
            </Text>
          </HStack>
          <Divider />
          <Text>{JSON.stringify(user)}</Text>
        </>
      )}
    </View>
  );
}
