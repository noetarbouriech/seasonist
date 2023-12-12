import { Pressable } from "@gluestack-ui/themed";
import { router } from "expo-router";
import { ArrowLeft } from "lucide-react-native";

export default function BackButton() {
  return (
    <Pressable onPress={() => router.back()}>
      <ArrowLeft color="black" />
    </Pressable>
  );
}
