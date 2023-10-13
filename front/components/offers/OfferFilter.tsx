import {
  Actionsheet,
  ActionsheetBackdrop,
  ActionsheetContent,
  ActionsheetDragIndicator,
  ActionsheetDragIndicatorWrapper,
  ActionsheetItem,
  ActionsheetItemText,
} from "@gluestack-ui/themed";
import { useState } from "react";

export default function OfferFilter() {
  const [showActionsheet, setShowActionsheet] = useState(false);
  const handleClose = () => setShowActionsheet(!showActionsheet);

  return (
    <Actionsheet isOpen={showActionsheet} onClose={handleClose} zIndex={999}>
      <ActionsheetBackdrop />
      <ActionsheetContent h="$72" zIndex={999}>
        <ActionsheetDragIndicatorWrapper>
          <ActionsheetDragIndicator />
        </ActionsheetDragIndicatorWrapper>
        <ActionsheetItem onPress={handleClose}>
          <ActionsheetItemText>Delete</ActionsheetItemText>
        </ActionsheetItem>
        <ActionsheetItem onPress={handleClose}>
          <ActionsheetItemText>Share</ActionsheetItemText>
        </ActionsheetItem>
        <ActionsheetItem onPress={handleClose}>
          <ActionsheetItemText>Play</ActionsheetItemText>
        </ActionsheetItem>
        <ActionsheetItem onPress={handleClose}>
          <ActionsheetItemText>Favourite</ActionsheetItemText>
        </ActionsheetItem>
        <ActionsheetItem onPress={handleClose}>
          <ActionsheetItemText>Cancel</ActionsheetItemText>
        </ActionsheetItem>
      </ActionsheetContent>
    </Actionsheet>
  );
}
