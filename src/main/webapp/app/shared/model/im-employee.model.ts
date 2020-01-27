import { Moment } from 'moment';
import { IQnowUser } from 'app/shared/model/qnow-user.model';
import { IEmployeeStatus } from 'app/shared/model/employee-status.model';
import { IDepartment } from 'app/shared/model/department.model';
import { IImEmployee } from 'app/shared/model/im-employee.model';
import { IUserContact } from 'app/shared/model/user-contact.model';

export interface IImEmployee {
  id?: number;
  jobTitle?: string;
  jobDescription?: string;
  availability?: number;
  ssNumber?: string;
  salary?: number;
  socialSecurity?: number;
  insurance?: number;
  otherCosts?: number;
  currency?: string;
  dependantP?: string;
  onlyJobP?: string;
  marriedP?: string;
  headOfHouseholdP?: string;
  birthdate?: Moment;
  hourlyCost?: number;
  qnowUser?: IQnowUser;
  employeeStatus?: IEmployeeStatus;
  departmentId?: IDepartment;
  supervisorId?: IImEmployee;
  userContacts?: IUserContact[];
}

export class ImEmployee implements IImEmployee {
  constructor(
    public id?: number,
    public jobTitle?: string,
    public jobDescription?: string,
    public availability?: number,
    public ssNumber?: string,
    public salary?: number,
    public socialSecurity?: number,
    public insurance?: number,
    public otherCosts?: number,
    public currency?: string,
    public dependantP?: string,
    public onlyJobP?: string,
    public marriedP?: string,
    public headOfHouseholdP?: string,
    public birthdate?: Moment,
    public hourlyCost?: number,
    public qnowUser?: IQnowUser,
    public employeeStatus?: IEmployeeStatus,
    public departmentId?: IDepartment,
    public supervisorId?: IImEmployee,
    public userContacts?: IUserContact[]
  ) {}
}
