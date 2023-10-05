import Octicons from "@expo/vector-icons/Octicons";
import { OpenSans_400Regular } from "@expo-google-fonts/open-sans";
import { GluestackUIProvider } from "@gluestack-ui/themed";
import { loadAsync } from "expo-font";
import { SplashScreen, Stack } from "expo-router";
import { useCallback, useEffect, useState } from "react";

export default function RootLayout() {
  const [appIsReady, setAppIsReady] = useState(false);

  useEffect(() => {
    async function prepare() {
      try {
        await loadAsync({ OpenSans_400Regular, ...Octicons.font });
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
      <Stack initialRouteName="index" screenOptions={{ headerShown: false }} />
    </GluestackUIProvider>
  );
}
