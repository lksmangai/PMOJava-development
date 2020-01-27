import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProjectClass } from 'app/shared/model/project-class.model';

type EntityResponseType = HttpResponse<IProjectClass>;
type EntityArrayResponseType = HttpResponse<IProjectClass[]>;

@Injectable({ providedIn: 'root' })
export class ProjectClassService {
  public resourceUrl = SERVER_API_URL + 'api/project-classes';

  constructor(protected http: HttpClient) {}

  create(projectClass: IProjectClass): Observable<EntityResponseType> {
    return this.http.post<IProjectClass>(this.resourceUrl, projectClass, { observe: 'response' });
  }

  update(projectClass: IProjectClass): Observable<EntityResponseType> {
    return this.http.put<IProjectClass>(this.resourceUrl, projectClass, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProjectClass>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProjectClass[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
