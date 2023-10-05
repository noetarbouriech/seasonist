import { Center, Heading, Image } from "@gluestack-ui/themed";
import { Link } from "expo-router";
import { View } from "react-native";

export default function Home() {
  return (
    <View>
      <Center h="$full" gap="$10">
        <Heading size="2xl">Index page</Heading>
        <Image source={require("../assets/images/dog.jpg")} alt="dog" />
        <Link href="/other">Go to other page</Link>
      </Center>
    </View>
  );
}
