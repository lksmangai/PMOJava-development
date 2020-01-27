import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProjectRoles } from 'app/shared/model/project-roles.model';

type EntityResponseType = HttpResponse<IProjectRoles>;
type EntityArrayResponseType = HttpResponse<IProjectRoles[]>;

@Injectable({ providedIn: 'root' })
export class ProjectRolesService {
  public resourceUrl = SERVER_API_URL + 'api/project-roles';

  constructor(protected http: HttpClient) {}

  create(projectRoles: IProjectRoles): Observable<EntityResponseType> {
    return this.http.post<IProjectRoles>(this.resourceUrl, projectRoles, { observe: 'response' });
  }

  update(projectRoles: IProjectRoles): Observable<EntityResponseType> {
    return this.http.put<IProjectRoles>(this.resourceUrl, projectRoles, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProjectRoles>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProjectRoles[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
