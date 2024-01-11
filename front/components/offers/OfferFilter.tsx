import { Heading, Image, Pressable, VStack } from "@gluestack-ui/themed";
import { useWindowDimensions } from "react-native";
import ActionSheet, { SheetProps } from "react-native-actions-sheet";
import Carousel from "react-native-reanimated-carousel";

const placeholderCategories = [
  {
    id: "0",
    title: "Vineyard",
    imageUrl: "https://images.unsplash.com/photo-1597337726353-26512fbe80c6",
  },
  {
    id: "1",
    title: "Bar",
    imageUrl: "https://images.unsplash.com/photo-1595751866979-de6e9d606220",
  },
  {
    id: "2",
    title: "Market",
    imageUrl: "https://images.unsplash.com/photo-1533900298318-6b8da08a523e",
  },
  {
    id: "3",
    title: "Factory",
    imageUrl: "https://images.unsplash.com/photo-1538080204498-afe921550d75",
  },
];

export default function OfferFilter(props: SheetProps) {
  const { width: windowWidth } = useWindowDimensions();

  return (
    <ActionSheet
      id={props.sheetId}
      gestureEnabled
      containerStyle={{
        height: 325,
        paddingTop: 5,
      }}
    >
      <Heading size="lg" fontWeight="$light" pl="$6">
        Categories
      </Heading>
      <Carousel
        width={windowWidth}
        height={250}
        data={placeholderCategories}
        renderItem={({ index, item }) => (
          <Category
            key={index}
            id={item.id}
            title={item.title}
            imageUrl={item.imageUrl}
          />
        )}
        mode="parallax"
        modeConfig={{
          parallaxScrollingScale: 0.92,
          parallaxScrollingOffset: 50,
        }}
      />
    </ActionSheet>
  );
}

type CategoryProps = {
  id: string;
  title: string;
  imageUrl: string;
};

function Category(props: CategoryProps) {
  return (
    <Pressable
      onPress={() => console.log(`filter by category ${props.id}`)}
      sx={{
        flex: 1,
        p: "$2",
        rounded: "$xl",
        ":active": {
          backgroundColor: "$backgroundDark100",
        },
      }}
    >
      <VStack flex={1}>
        <Image
          flex={1}
          w="$full"
          rounded="$xl"
          source={props.imageUrl}
          alt={`${props.title} category image`}
        />
        <Heading size="md" fontWeight="$semibold">
          {props.title}
        </Heading>
      </VStack>
    </Pressable>
  );
}
