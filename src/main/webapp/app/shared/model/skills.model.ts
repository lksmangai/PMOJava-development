import { ISkills } from 'app/shared/model/skills.model';
import { ISkillCategory } from 'app/shared/model/skill-category.model';

export interface ISkills {
  id?: number;
  skillCode?: string;
  skillName?: string;
  parentSkillsId?: ISkills;
  skillCategoryId?: ISkillCategory;
}

export class Skills implements ISkills {
  constructor(
    public id?: number,
    public skillCode?: string,
    public skillName?: string,
    public parentSkillsId?: ISkills,
    public skillCategoryId?: ISkillCategory
  ) {}
}
