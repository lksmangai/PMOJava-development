import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProjectBusinessgoalId } from 'app/shared/model/project-businessgoal-id.model';

type EntityResponseType = HttpResponse<IProjectBusinessgoalId>;
type EntityArrayResponseType = HttpResponse<IProjectBusinessgoalId[]>;

@Injectable({ providedIn: 'root' })
export class ProjectBusinessgoalIdService {
  public resourceUrl = SERVER_API_URL + 'api/project-businessgoal-ids';

  constructor(protected http: HttpClient) {}

  create(projectBusinessgoalId: IProjectBusinessgoalId): Observable<EntityResponseType> {
    return this.http.post<IProjectBusinessgoalId>(this.resourceUrl, projectBusinessgoalId, { observe: 'response' });
  }

  update(projectBusinessgoalId: IProjectBusinessgoalId): Observable<EntityResponseType> {
    return this.http.put<IProjectBusinessgoalId>(this.resourceUrl, projectBusinessgoalId, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProjectBusinessgoalId>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProjectBusinessgoalId[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
