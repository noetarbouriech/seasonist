import { ScrollView } from "@gluestack-ui/themed";

import Header from "../components/Header";
import OfferList from "../components/offers/OfferList";

export default function Home() {
  return (
    <ScrollView w="$full" minHeight="$full" bgColor="$white">
      <Header title="👋 Welcome" search={{ placeholder: "Search an offer" }} />
      <OfferList title="Recommended for you" />
    </ScrollView>
  );
}
