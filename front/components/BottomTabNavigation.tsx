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
import { SheetManager } from "react-native-actions-sheet";

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
        justifyContent="center"
        borderTopColor="$secondary0"
        borderTopWidth={2}
        px={25}
      >
        <Tab
          label="Home"
          icon={<HomeIcon />}
          isActive={activePage === Page.Home}
          href="/home"
        />

        <Tab
          label="Categories"
          icon={<FilterIcon />}
          onPress={() => {
            router.push("/home");
            SheetManager.show("offer-filter");
          }}
        />

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
      w="25%"
      h={60}
    >
      <VStack h="$full" alignItems="center" justifyContent="center">
        {icon}
        <Text size="sm" color={textColor}>
          {props.label}
        </Text>
      </VStack>
    </Pressable>
  );
}
