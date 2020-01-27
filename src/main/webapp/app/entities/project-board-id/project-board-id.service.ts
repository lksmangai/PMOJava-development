import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProjectBoardId } from 'app/shared/model/project-board-id.model';

type EntityResponseType = HttpResponse<IProjectBoardId>;
type EntityArrayResponseType = HttpResponse<IProjectBoardId[]>;

@Injectable({ providedIn: 'root' })
export class ProjectBoardIdService {
  public resourceUrl = SERVER_API_URL + 'api/project-board-ids';

  constructor(protected http: HttpClient) {}

  create(projectBoardId: IProjectBoardId): Observable<EntityResponseType> {
    return this.http.post<IProjectBoardId>(this.resourceUrl, projectBoardId, { observe: 'response' });
  }

  update(projectBoardId: IProjectBoardId): Observable<EntityResponseType> {
    return this.http.put<IProjectBoardId>(this.resourceUrl, projectBoardId, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProjectBoardId>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProjectBoardId[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
