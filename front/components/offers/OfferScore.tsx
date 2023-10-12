import { HStack, Text } from "@gluestack-ui/themed";
import { StarIcon } from "lucide-react-native";

type Props = {
  score: number;
};

export default function OfferScore(props: Props) {
  return (
    <HStack alignItems="center">
      {Array(5)
        .fill(null)
        .map((_, i) => (
          <StarIcon
            key={i}
            size="16"
            color="black"
            fill={i + 1 <= Math.trunc(props.score) ? "black" : "transparent"}
          />
        ))}
      <Text ml="$1" fontWeight="$bold">
        {props.score}
      </Text>
    </HStack>
  );
}
