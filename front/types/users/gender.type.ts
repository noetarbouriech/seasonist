export type Gender = "MALE" | "FEMALE" | "OTHER" | "PREFER_NOT_TO_STATE";

export function genderToString(gender: Gender) {
  switch (gender) {
    case "MALE":
      return "Male";
    case "FEMALE":
      return "Female";
    case "OTHER":
      return "Other";
    case "PREFER_NOT_TO_STATE":
      return "Prefer not to state";
  }
}
