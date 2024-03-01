import { JobCategory } from "../common/job-category.type";

export type Experience = {
  id: string;
  jobTitle: string;
  dateStart: string;
  dateEnd: string | null;
  description: string | null;
  jobCategory: JobCategory;
  companyId: string | null;
};
