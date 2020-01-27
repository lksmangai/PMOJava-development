import { IRoles } from 'app/shared/model/roles.model';
import { IUserContact } from 'app/shared/model/user-contact.model';

export interface IGroupMembers {
  id?: number;
  code?: string;
  name?: string;
  description?: string;
  roles?: IRoles[];
  userContacts?: IUserContact[];
}

export class GroupMembers implements IGroupMembers {
  constructor(
    public id?: number,
    public code?: string,
    public name?: string,
    public description?: string,
    public roles?: IRoles[],
    public userContacts?: IUserContact[]
  ) {}
}
