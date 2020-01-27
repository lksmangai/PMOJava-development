import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProjectTypeId } from 'app/shared/model/project-type-id.model';

type EntityResponseType = HttpResponse<IProjectTypeId>;
type EntityArrayResponseType = HttpResponse<IProjectTypeId[]>;

@Injectable({ providedIn: 'root' })
export class ProjectTypeIdService {
  public resourceUrl = SERVER_API_URL + 'api/project-type-ids';

  constructor(protected http: HttpClient) {}

  create(projectTypeId: IProjectTypeId): Observable<EntityResponseType> {
    return this.http.post<IProjectTypeId>(this.resourceUrl, projectTypeId, { observe: 'response' });
  }

  update(projectTypeId: IProjectTypeId): Observable<EntityResponseType> {
    return this.http.put<IProjectTypeId>(this.resourceUrl, projectTypeId, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProjectTypeId>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProjectTypeId[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
