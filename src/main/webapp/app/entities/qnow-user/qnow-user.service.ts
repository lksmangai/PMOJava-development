import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IQnowUser } from 'app/shared/model/qnow-user.model';

type EntityResponseType = HttpResponse<IQnowUser>;
type EntityArrayResponseType = HttpResponse<IQnowUser[]>;

@Injectable({ providedIn: 'root' })
export class QnowUserService {
  public resourceUrl = SERVER_API_URL + 'api/qnow-users';

  constructor(protected http: HttpClient) {}

  create(qnowUser: IQnowUser): Observable<EntityResponseType> {
    return this.http.post<IQnowUser>(this.resourceUrl, qnowUser, { observe: 'response' });
  }

  update(qnowUser: IQnowUser): Observable<EntityResponseType> {
    return this.http.put<IQnowUser>(this.resourceUrl, qnowUser, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IQnowUser>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IQnowUser[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
