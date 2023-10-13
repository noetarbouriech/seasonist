import { Box, KeyboardAvoidingView, ScrollView } from "@gluestack-ui/themed";
import { View } from "react-native";
import Svg, { G, Path, Defs, ClipPath } from "react-native-svg";

export default function LoginPattern({ children }: any) {
  return (
    <View
      style={{ flex: 1, flexShrink: 0, height: "100%", overflow: "hidden" }}
    >
      <Svg
        width="100%"
        height={332}
        fill="none"
        style={{ top: 0, position: "absolute" }}
      >
        <G clipPath="url(#a)">
          <Path
            fill="#BEF264"
            d="m1114 44-48 16c-48 16-144 48-240 74.7-96 26.3-192 48.3-288 32C442 151 346 97 250 81.3 154 65 58 87-38 81.3-134 76-230 44-278 28l-48-16h1440v32Z"
          />
        </G>
        <G clipPath="url(#b)">
          <Path
            fill="#ECFCCB"
            d="m1149 32-48 16c-48 16-144 48-240 74.7-96 26.3-192 48.3-288 32C477 139 381 85 285 69.3 189 53 93 75-3 69.3-99 64-195 32-243 16l-48-16h1440v32Z"
          />
        </G>
        <Defs>
          <ClipPath id="a">
            <Path fill="#fff" d="M1114 332H-326V12h1440z" />
          </ClipPath>
          <ClipPath id="b">
            <Path fill="#fff" d="M1149 320H-291V0h1440z" />
          </ClipPath>
        </Defs>
      </Svg>

      <KeyboardAvoidingView flex={1}>{children}</KeyboardAvoidingView>

      <Svg
        width="100%"
        height={347}
        fill="none"
        style={{ bottom: -30, position: "absolute", zIndex: -1 }}
      >
        <G clipPath="url(#a)">
          <Path
            fill="#BEF264"
            d="m-503 288 48-16c48-16 144-48 240-74.7 96-26.3 192-48.3 288-32 96 15.7 192 69.7 288 85.4 96 16.3 192-5.7 288 0C745 256 841 288 889 304l48 16H-503v-32Z"
          />
        </G>
        <G clipPath="url(#b)">
          <Path
            fill="#ECFCCB"
            d="m-466 315 48-16c48-16 144-48 240-74.7 96-26.3 192-48.3 288-32 96 15.7 192 69.7 288 85.4 96 16.3 192-5.7 288 0C782 283 878 315 926 331l48 16H-466v-32Z"
          />
        </G>
        <Defs>
          <ClipPath id="a">
            <Path fill="#fff" d="M-503 0H937v320H-503z" />
          </ClipPath>
          <ClipPath id="b">
            <Path fill="#fff" d="M-466 27H974v320H-466z" />
          </ClipPath>
        </Defs>
      </Svg>
    </View>
  );
}
