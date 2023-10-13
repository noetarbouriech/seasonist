import { OpenSans_400Regular } from "@expo-google-fonts/open-sans";
import { GluestackUIProvider } from "@gluestack-ui/themed";
import { loadAsync } from "expo-font";
import { SplashScreen, Tabs } from "expo-router";
import { Home } from "lucide-react-native";
import { useCallback, useEffect, useState } from "react";

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
      <Tabs screenOptions={{ headerShown: false }}>
        <Tabs.Screen
          name="index"
          options={{
            href: "/",
            title: "Home",
            tabBarIcon: () => <Home color="#84CC16" size={25} />,
            tabBarLabelStyle: {
              color: "#525252",
              marginTop: 0,
              fontSize: 12,
              marginBottom: 6,
            },
          }}
        />
        <Tabs.Screen name="other" options={{ href: null }} />
        <Tabs.Screen name="offers/[id]" options={{ href: null }} />
      </Tabs>
    </GluestackUIProvider>
  );
}
