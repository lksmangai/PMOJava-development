import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProjectInitiativeId } from 'app/shared/model/project-initiative-id.model';

type EntityResponseType = HttpResponse<IProjectInitiativeId>;
type EntityArrayResponseType = HttpResponse<IProjectInitiativeId[]>;

@Injectable({ providedIn: 'root' })
export class ProjectInitiativeIdService {
  public resourceUrl = SERVER_API_URL + 'api/project-initiative-ids';

  constructor(protected http: HttpClient) {}

  create(projectInitiativeId: IProjectInitiativeId): Observable<EntityResponseType> {
    return this.http.post<IProjectInitiativeId>(this.resourceUrl, projectInitiativeId, { observe: 'response' });
  }

  update(projectInitiativeId: IProjectInitiativeId): Observable<EntityResponseType> {
    return this.http.put<IProjectInitiativeId>(this.resourceUrl, projectInitiativeId, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProjectInitiativeId>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProjectInitiativeId[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
