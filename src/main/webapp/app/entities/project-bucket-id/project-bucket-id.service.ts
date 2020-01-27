import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProjectBucketId } from 'app/shared/model/project-bucket-id.model';

type EntityResponseType = HttpResponse<IProjectBucketId>;
type EntityArrayResponseType = HttpResponse<IProjectBucketId[]>;

@Injectable({ providedIn: 'root' })
export class ProjectBucketIdService {
  public resourceUrl = SERVER_API_URL + 'api/project-bucket-ids';

  constructor(protected http: HttpClient) {}

  create(projectBucketId: IProjectBucketId): Observable<EntityResponseType> {
    return this.http.post<IProjectBucketId>(this.resourceUrl, projectBucketId, { observe: 'response' });
  }

  update(projectBucketId: IProjectBucketId): Observable<EntityResponseType> {
    return this.http.put<IProjectBucketId>(this.resourceUrl, projectBucketId, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProjectBucketId>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProjectBucketId[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
