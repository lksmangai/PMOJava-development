import { ISkillExpertise } from 'app/shared/model/skill-expertise.model';
import { IUserContact } from 'app/shared/model/user-contact.model';
import { IImProjects } from 'app/shared/model/im-projects.model';
import { ISkills } from 'app/shared/model/skills.model';

export interface ISkillTable {
  id?: number;
  skillExpertise?: ISkillExpertise;
  userContact?: IUserContact;
  imProjects?: IImProjects;
  skills?: ISkills;
}

export class SkillTable implements ISkillTable {
  constructor(
    public id?: number,
    public skillExpertise?: ISkillExpertise,
    public userContact?: IUserContact,
    public imProjects?: IImProjects,
    public skills?: ISkills
  ) {}
}
