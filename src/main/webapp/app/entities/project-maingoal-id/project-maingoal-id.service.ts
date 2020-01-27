import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProjectMaingoalId } from 'app/shared/model/project-maingoal-id.model';

type EntityResponseType = HttpResponse<IProjectMaingoalId>;
type EntityArrayResponseType = HttpResponse<IProjectMaingoalId[]>;

@Injectable({ providedIn: 'root' })
export class ProjectMaingoalIdService {
  public resourceUrl = SERVER_API_URL + 'api/project-maingoal-ids';

  constructor(protected http: HttpClient) {}

  create(projectMaingoalId: IProjectMaingoalId): Observable<EntityResponseType> {
    return this.http.post<IProjectMaingoalId>(this.resourceUrl, projectMaingoalId, { observe: 'response' });
  }

  update(projectMaingoalId: IProjectMaingoalId): Observable<EntityResponseType> {
    return this.http.put<IProjectMaingoalId>(this.resourceUrl, projectMaingoalId, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProjectMaingoalId>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProjectMaingoalId[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
