import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRoles } from 'app/shared/model/roles.model';

type EntityResponseType = HttpResponse<IRoles>;
type EntityArrayResponseType = HttpResponse<IRoles[]>;

@Injectable({ providedIn: 'root' })
export class RolesService {
  public resourceUrl = SERVER_API_URL + 'api/roles';

  constructor(protected http: HttpClient) {}

  create(roles: IRoles): Observable<EntityResponseType> {
    return this.http.post<IRoles>(this.resourceUrl, roles, { observe: 'response' });
  }

  update(roles: IRoles): Observable<EntityResponseType> {
    return this.http.put<IRoles>(this.resourceUrl, roles, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRoles>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRoles[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
