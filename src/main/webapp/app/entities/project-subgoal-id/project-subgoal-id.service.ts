import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProjectSubgoalId } from 'app/shared/model/project-subgoal-id.model';

type EntityResponseType = HttpResponse<IProjectSubgoalId>;
type EntityArrayResponseType = HttpResponse<IProjectSubgoalId[]>;

@Injectable({ providedIn: 'root' })
export class ProjectSubgoalIdService {
  public resourceUrl = SERVER_API_URL + 'api/project-subgoal-ids';

  constructor(protected http: HttpClient) {}

  create(projectSubgoalId: IProjectSubgoalId): Observable<EntityResponseType> {
    return this.http.post<IProjectSubgoalId>(this.resourceUrl, projectSubgoalId, { observe: 'response' });
  }

  update(projectSubgoalId: IProjectSubgoalId): Observable<EntityResponseType> {
    return this.http.put<IProjectSubgoalId>(this.resourceUrl, projectSubgoalId, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProjectSubgoalId>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProjectSubgoalId[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
