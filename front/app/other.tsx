import { Center, Heading } from "@gluestack-ui/themed";
import { Link } from "expo-router";
import { View } from "react-native";

export default function Other() {
  return (
    <View>
      <Center h="$full" gap="$10">
        <Heading size="2xl">Other page</Heading>
        <Link href="/">Go to index page</Link>
      </Center>
    </View>
  );
}
