import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProjectTag } from 'app/shared/model/project-tag.model';

type EntityResponseType = HttpResponse<IProjectTag>;
type EntityArrayResponseType = HttpResponse<IProjectTag[]>;

@Injectable({ providedIn: 'root' })
export class ProjectTagService {
  public resourceUrl = SERVER_API_URL + 'api/project-tags';

  constructor(protected http: HttpClient) {}

  create(projectTag: IProjectTag): Observable<EntityResponseType> {
    return this.http.post<IProjectTag>(this.resourceUrl, projectTag, { observe: 'response' });
  }

  update(projectTag: IProjectTag): Observable<EntityResponseType> {
    return this.http.put<IProjectTag>(this.resourceUrl, projectTag, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProjectTag>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProjectTag[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
