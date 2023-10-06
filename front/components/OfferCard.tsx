import {
  Badge,
  BadgeText,
  Box,
  HStack,
  Heading,
  Pressable,
  Text,
} from "@gluestack-ui/themed";
import { useRouter } from "expo-router";

type Props = {
  job: {
    id: number;
    title: string;
    company: string;
    location: string;
    tags: string[];
    postedAt: string;
  };
};

export default function OfferCard(props: Props) {
  const router = useRouter();

  return (
    <Pressable
      sx={{
        ":active": {
          bgColor: "$backgroundLightMuted",
        },
      }}
      onPress={() => router.push("/other")}
    >
      <Box
        rowGap="$1"
        py="$2"
        pl="$6"
        borderRadius="$xl"
        borderColor="$backgroundLightMuted"
        borderWidth={2}
      >
        <Heading>{props.job.title}</Heading>
        <Box>
          <Text>👔 {props.job.company}</Text>
          <Text fontWeight="$light">📍 {props.job.location}</Text>
        </Box>
        <HStack columnGap="$4">
          {props.job.tags.map((tag, i) => (
            <Badge key={i} bgColor="$backgroundLightMuted">
              <BadgeText color="$secondary600">{tag}</BadgeText>
            </Badge>
          ))}
        </HStack>
        <Text fontWeight="$light" size="xs">
          Posted {props.job.postedAt}
        </Text>
      </Box>
    </Pressable>
  );
}
