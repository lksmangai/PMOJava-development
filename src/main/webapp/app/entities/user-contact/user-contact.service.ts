import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IUserContact } from 'app/shared/model/user-contact.model';

type EntityResponseType = HttpResponse<IUserContact>;
type EntityArrayResponseType = HttpResponse<IUserContact[]>;

@Injectable({ providedIn: 'root' })
export class UserContactService {
  public resourceUrl = SERVER_API_URL + 'api/user-contacts';

  constructor(protected http: HttpClient) {}

  create(userContact: IUserContact): Observable<EntityResponseType> {
    return this.http.post<IUserContact>(this.resourceUrl, userContact, { observe: 'response' });
  }

  update(userContact: IUserContact): Observable<EntityResponseType> {
    return this.http.put<IUserContact>(this.resourceUrl, userContact, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUserContact>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserContact[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
