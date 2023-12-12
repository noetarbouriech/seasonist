import {
  Button,
  ButtonText,
  HStack,
  Heading,
  ScrollView,
  Text,
  VStack,
} from "@gluestack-ui/themed";
import { useLocalSearchParams } from "expo-router";
import { InfoIcon } from "lucide-react-native";

import BackButton from "../../components/BackButton";
import OfferScore from "../../components/offers/OfferScore";
import OfferTableInfo from "../../components/offers/OfferTableInfo";

export default function OfferDetail() {
  const { id } = useLocalSearchParams();

  const defaultJob = {
    id,
    title: "Landscape Gardener",
    company: "Morgan Sindall Property Service",
    score: 4.3,
    location: "Leeds, Yorkshire, UK",
    tags: ["Full-time", "Garderning", "Garderning", "Garderning"],
    postedAt: "30+ days ago",
    about:
      "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. At quis risus sed vulputate odio. Fermentum iaculis eu non diam phasellus vestibulum lorem. Etiam erat velit scelerisque in dictum. Penatibus et magnis dis parturient montes. Platea dictumst quisque sagittis purus sit amet. Morbi tincidunt ornare massa eget egestas purus. Commodo odio aenean sed adipiscing diam donec adipiscing tristique.\n\nA arcu cursus vitae congue mauris rhoncus aenean. Suscipit tellus mauris a diam. Nibh ipsum consequat nisl vel pretium lectus quam id leo. Donec adipiscing tristique risus nec feugiat in fermentum. Ut morbi tincidunt augue interdum velit euismod. Tortor consequat id porta nibh venenatis. Sodales ut etiam sit amet nisl purus in mollis. Cursus eget nunc scelerisque viverra mauris in aliquam.",
  };

  return (
    <ScrollView>
      <VStack pt="$12" pb="$4" px="$8" bgColor="$white">
        <BackButton />
        <VStack pt="$4" space="2xl">
          <VStack rowGap="$2">
            <Heading size="2xl">{defaultJob.title}</Heading>
            <VStack gap="$1">
              <Text color="$lime500" fontWeight="semibold" size="xl">
                {defaultJob.company}
              </Text>
              <OfferScore score={defaultJob.score} />
              <Text size="md">{defaultJob.location}</Text>
            </VStack>
          </VStack>

          <OfferTableInfo
            offerInfoList={[
              { title: "Salary", value: "£12.31/hour" },
              { title: "Job tags", value: "Full-time, Gardening" },
              { title: "Date Start", value: "July 1st, 2023" },
              { title: "Date End", value: "September 31, 2023" },
              { title: "Posted", value: "30+ days ago" },
            ]}
          />

          <VStack space="sm">
            <HStack space="sm" alignItems="center">
              <InfoIcon size="20" color="black" />
              <Heading size="xl">About</Heading>
            </HStack>
            <Text>{defaultJob.about}</Text>
          </VStack>

          <HStack justifyContent="center" px="$4">
            <Button h="$12" w="$full" bgColor="$lime500">
              <ButtonText fontSize="$xl" color="$white">
                Apply
              </ButtonText>
            </Button>
          </HStack>
        </VStack>
      </VStack>
    </ScrollView>
  );
}
