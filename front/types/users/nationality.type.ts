export type Nationality =
  | "FRANCE"
  | "BELGIUM"
  | "GERMANY"
  | "ITALY"
  | "SPAIN"
  | "SWEDEN"
  | "SWISS"
  | "UNITED_KINGDOM"
  | "OTHER";

export function nationalityToString(nationality: Nationality) {
  switch (nationality) {
    case "FRANCE":
      return "France";
    case "BELGIUM":
      return "Belgium";
    case "GERMANY":
      return "Germany";
    case "ITALY":
      return "Italy";
    case "SPAIN":
      return "Spain";
    case "SWEDEN":
      return "Sweden";
    case "SWISS":
      return "Swiss";
    case "UNITED_KINGDOM":
      return "United Kingdom";
    case "OTHER":
      return "Other";
  }
}
