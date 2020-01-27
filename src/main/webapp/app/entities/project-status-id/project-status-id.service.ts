import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProjectStatusId } from 'app/shared/model/project-status-id.model';

type EntityResponseType = HttpResponse<IProjectStatusId>;
type EntityArrayResponseType = HttpResponse<IProjectStatusId[]>;

@Injectable({ providedIn: 'root' })
export class ProjectStatusIdService {
  public resourceUrl = SERVER_API_URL + 'api/project-status-ids';

  constructor(protected http: HttpClient) {}

  create(projectStatusId: IProjectStatusId): Observable<EntityResponseType> {
    return this.http.post<IProjectStatusId>(this.resourceUrl, projectStatusId, { observe: 'response' });
  }

  update(projectStatusId: IProjectStatusId): Observable<EntityResponseType> {
    return this.http.put<IProjectStatusId>(this.resourceUrl, projectStatusId, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProjectStatusId>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProjectStatusId[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
