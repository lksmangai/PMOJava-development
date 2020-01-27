import { IUser } from 'app/core/user/user.model';

export interface IQnowUser {
  id?: number;
  user?: IUser;
}

export class QnowUser implements IQnowUser {
  constructor(public id?: number, public user?: IUser) {}
}
