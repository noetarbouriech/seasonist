import { Center, Heading, View } from "@gluestack-ui/themed";
import { Link } from "expo-router";

export default function Other() {
  return (
    <View>
      <Center h="$full" gap="$10">
        <Heading size="xl">Page not available, sorry...</Heading>
        <Link href="/">Go to homepage</Link>
      </Center>
    </View>
  );
}
