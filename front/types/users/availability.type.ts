import { JobCategory } from "../common/job-category.type";

export type Availability = {
  dateStart: string;
  dateEnd: string;
  jobCategory: JobCategory;
  searchArea: string;
};
