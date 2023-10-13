import { Heading, VStack } from "@gluestack-ui/themed";

import OfferCard from "./OfferCard";

type Props = {
  title?: string;
};

export default function OfferList(props: Props) {
  const defaultJob = {
    id: 0,
    title: "Landscape Gardener",
    company: "Morgan Sindall Property Service",
    location: "Leeds, Yorkshire, UK",
    tags: ["Full-time", "Garderning"],
    postedAt: "30+ days ago",
  };

  return (
    <VStack px="$4" pt="$8" pb="$4" space="sm">
      <Heading pl="$4" size="xl">
        {props.title ?? "Offers"}
      </Heading>
      <VStack gap="$4">
        {Array(10)
          .fill(defaultJob)
          .map((job, i) => (
            <OfferCard key={i} job={job} />
          ))}
      </VStack>
    </VStack>
  );
}
