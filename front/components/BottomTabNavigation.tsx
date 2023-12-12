import {
  HStack,
  Pressable,
  Text,
  VStack,
  useToken,
} from "@gluestack-ui/themed";
import { router, useSegments } from "expo-router";
import {
  FilterIcon,
  HomeIcon,
  InboxIcon,
  UserCircleIcon,
} from "lucide-react-native";
import { ReactElement, cloneElement, useMemo } from "react";

import OfferFilter from "./offers/OfferFilter";

enum Page {
  Home,
  Inbox,
  Profile,
  Unknown,
}

export default function BottomTabNavigation() {
  const segments: string[] = useSegments();

  const activePage = useMemo((): Page => {
    if (segments.includes("home")) {
      return Page.Home;
    }

    if (segments.includes("inbox")) {
      return Page.Inbox;
    }

    if (segments.includes("profile")) {
      return Page.Profile;
    }

    return Page.Unknown;
  }, [segments]);

  return (
    <>
      <HStack
        h={60}
        alignItems="center"
        justifyContent="center"
        borderTopColor="$secondary0"
        borderTopWidth={2}
        px="$16"
      >
        <HStack justifyContent="space-between" w="$full">
          <Tab
            label="Home"
            icon={<HomeIcon />}
            isActive={activePage === Page.Home}
            href="/home"
          />

          <Tab label="Categories" icon={<FilterIcon />} />

          <Tab
            label="Inbox"
            icon={<InboxIcon />}
            isActive={activePage === Page.Inbox}
            href="/other"
          />

          <Tab
            label="Profile"
            icon={<UserCircleIcon />}
            isActive={activePage === Page.Profile}
            href="/other"
          />
        </HStack>
      </HStack>
      <OfferFilter />
    </>
  );
}

type TabProps = {
  icon: ReactElement;
  label: string;
  isActive?: boolean;
  onPress?: () => void;
  href?: string;
};

function Tab(props: TabProps) {
  const activeColor = useToken("colors", "lime500");
  const textColor = useToken("colors", "secondary600");

  // inject
  const icon = cloneElement(props.icon, {
    color: props.isActive ? activeColor : textColor,
    size: 25,
  });

  return (
    <Pressable
      onPress={() => {
        // @ts-ignore
        if (props.href) router.push(props.href);
        if (props.onPress) props.onPress();
      }}
    >
      <VStack alignItems="center">
        {icon}
        <Text size="sm" color={textColor}>
          {props.label}
        </Text>
      </VStack>
    </Pressable>
  );
}
