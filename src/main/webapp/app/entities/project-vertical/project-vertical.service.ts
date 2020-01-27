import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProjectVertical } from 'app/shared/model/project-vertical.model';

type EntityResponseType = HttpResponse<IProjectVertical>;
type EntityArrayResponseType = HttpResponse<IProjectVertical[]>;

@Injectable({ providedIn: 'root' })
export class ProjectVerticalService {
  public resourceUrl = SERVER_API_URL + 'api/project-verticals';

  constructor(protected http: HttpClient) {}

  create(projectVertical: IProjectVertical): Observable<EntityResponseType> {
    return this.http.post<IProjectVertical>(this.resourceUrl, projectVertical, { observe: 'response' });
  }

  update(projectVertical: IProjectVertical): Observable<EntityResponseType> {
    return this.http.put<IProjectVertical>(this.resourceUrl, projectVertical, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProjectVertical>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProjectVertical[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
