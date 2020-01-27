import { ITagType } from 'app/shared/model/tag-type.model';
import { IImEmployee } from 'app/shared/model/im-employee.model';
import { IImProjects } from 'app/shared/model/im-projects.model';

export interface IProjectTag {
  id?: number;
  code?: string;
  name?: string;
  description?: string;
  flag?: string;
  color?: string;
  tagType?: ITagType;
  imEmployee?: IImEmployee;
  imProjects?: IImProjects;
}

export class ProjectTag implements IProjectTag {
  constructor(
    public id?: number,
    public code?: string,
    public name?: string,
    public description?: string,
    public flag?: string,
    public color?: string,
    public tagType?: ITagType,
    public imEmployee?: IImEmployee,
    public imProjects?: IImProjects
  ) {}
}
