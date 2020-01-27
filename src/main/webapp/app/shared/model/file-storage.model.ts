import { IUserContact } from 'app/shared/model/user-contact.model';
import { IImProjects } from 'app/shared/model/im-projects.model';

export interface IFileStorage {
  id?: number;
  filename?: string;
  caption?: string;
  userContact?: IUserContact;
  imProjects?: IImProjects;
}

export class FileStorage implements IFileStorage {
  constructor(
    public id?: number,
    public filename?: string,
    public caption?: string,
    public userContact?: IUserContact,
    public imProjects?: IImProjects
  ) {}
}
