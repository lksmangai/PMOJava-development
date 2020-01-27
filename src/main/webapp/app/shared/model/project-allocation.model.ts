import { IImEmployee } from 'app/shared/model/im-employee.model';
import { IImProjects } from 'app/shared/model/im-projects.model';
import { IProjectRoles } from 'app/shared/model/project-roles.model';

export interface IProjectAllocation {
  id?: number;
  percentage?: number;
  imEmployee?: IImEmployee;
  imProjects?: IImProjects;
  projectRoles?: IProjectRoles;
}

export class ProjectAllocation implements IProjectAllocation {
  constructor(
    public id?: number,
    public percentage?: number,
    public imEmployee?: IImEmployee,
    public imProjects?: IImProjects,
    public projectRoles?: IProjectRoles
  ) {}
}
