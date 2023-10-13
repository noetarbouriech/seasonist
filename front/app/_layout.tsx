import { OpenSans_400Regular } from "@expo-google-fonts/open-sans";
import { GluestackUIProvider } from "@gluestack-ui/themed";
import { loadAsync } from "expo-font";
import { SplashScreen, Stack } from "expo-router";
import { useCallback, useEffect, useState } from "react";

import BottomTabNavigation from "../components/BottomTabNavigation";

export default function RootLayout() {
  const [appIsReady, setAppIsReady] = useState(false);

  useEffect(() => {
    async function prepare() {
      try {
        await loadAsync({ OpenSans_400Regular });
      } catch (e) {
        console.warn(e);
      } finally {
        setAppIsReady(true);
      }
    }

    prepare();
  }, []);

  const onLayoutRootView = useCallback(async () => {
    if (appIsReady) await SplashScreen.hideAsync();
  }, [appIsReady]);

  if (!onLayoutRootView()) {
    return null;
  }

  return (
    <GluestackUIProvider>
      <Stack initialRouteName="/home" screenOptions={{ headerShown: false }} />
      <BottomTabNavigation />
    </GluestackUIProvider>
  );
}
