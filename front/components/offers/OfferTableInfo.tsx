import { HStack, Heading, VStack, Text } from "@gluestack-ui/themed";

type Props = {
  offerInfoList: {
    title: string;
    value: string;
  }[];
};

export default function OfferTableInfo(props: Props) {
  return (
    <VStack>
      {props.offerInfoList.map((info, i) => (
        <HStack
          key={i}
          alignItems="center"
          justifyContent="space-between"
          py="$3"
          borderBottomWidth={1}
          borderBottomColor="$borderDark200"
        >
          <Heading size="md" fontWeight="$medium" textTransform="uppercase">
            {info.title}
          </Heading>
          <Text fontWeight="$light">{info.value}</Text>
        </HStack>
      ))}
    </VStack>
  );
}
