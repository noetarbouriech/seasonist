import {
  Box,
  Heading,
  Input,
  InputField,
  InputIcon,
  InputSlot,
  SearchIcon,
} from "@gluestack-ui/themed";
import { ReactNode } from "react";

type Props = {
  title: string;
  search: {
    placeholder?: string;
    icon?: ReactNode;
  };
};

export default function Header(props: Props) {
  return (
    <Box position="relative">
      <Box
        position="absolute"
        bgColor="$lime300"
        bottom={-7}
        left={0}
        w="$full"
        h={50}
        px="$8"
        borderBottomLeftRadius={20}
        borderBottomRightRadius={20}
      />
      <Box
        bgColor="$lime100"
        gap="$2"
        pt="$20"
        pb="$4"
        px="$8"
        borderBottomLeftRadius={20}
        borderBottomRightRadius={20}
      >
        <Heading size="3xl">{props.title}</Heading>
        <Input variant="outline" size="md" rounded={11} bgColor="$white">
          <InputField placeholder={props.search.placeholder ?? "Search"} />
          <InputSlot w={40}>
            <InputIcon children={props.search.icon ?? <SearchIcon />} />
          </InputSlot>
        </Input>
      </Box>
    </Box>
  );
}
