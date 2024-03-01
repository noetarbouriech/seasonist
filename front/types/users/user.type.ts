import { Availability } from "./availability.type";
import { Experience } from "./experience.type";
import { Gender } from "./gender.type";
import { Nationality } from "./nationality.type";
import { Recommendation } from "./recommendation.type";

export type User = {
  id: string;
  firstname: string;
  lastname: string;
  email: string;
  phone: string;
  address: string;
  gender: Gender;
  bio: "";
  nationality: Nationality | null;
  recommendations: Recommendation[];
  experiences: Experience[];
  availability: Availability;
};
