import { OpenSans_400Regular } from "@expo-google-fonts/open-sans";
import { GluestackUIProvider } from "@gluestack-ui/themed";
import { loadAsync } from "expo-font";
import { SplashScreen, Stack, router } from "expo-router";
import { useCallback, useEffect, useState } from "react";
import { SheetProvider, registerSheet } from "react-native-actions-sheet";

import BottomTabNavigation from "../components/BottomTabNavigation";
import OfferFilter from "../components/offers/OfferFilter";

export default function RootLayout() {
  const [appIsReady, setAppIsReady] = useState(false);

  useEffect(() => {
    async function prepare() {
      try {
        registerSheet("offer-filter", OfferFilter);
        await loadAsync({ OpenSans_400Regular });
      } catch (e) {
        console.warn(e);
      } finally {
        setAppIsReady(true);
        router.replace("/login");
      }
    }

    prepare();
  }, []);

  const onLayoutRootView = useCallback(async () => {
    if (!appIsReady) return;

    await SplashScreen.hideAsync();
  }, [appIsReady]);

  if (!onLayoutRootView()) {
    return null;
  }

  return (
    <GluestackUIProvider>
      <SheetProvider>
        <Stack
          initialRouteName="/login"
          screenOptions={{ headerShown: false }}
        />
      </SheetProvider>
    </GluestackUIProvider>
  );
}
