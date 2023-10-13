import { Center, Heading, Image, View } from "@gluestack-ui/themed";
import { Link } from "expo-router";

export default function Other() {
  return (
    <View>
      <Center h="$full" gap="$10">
        <Heading size="2xl">Dog</Heading>
        <Image source={require("../assets/images/dog.jpg")} alt="dog" />
        <Link href="/">Go to home</Link>
      </Center>
    </View>
  );
}
