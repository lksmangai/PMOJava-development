import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProjectCostCenterId } from 'app/shared/model/project-cost-center-id.model';

type EntityResponseType = HttpResponse<IProjectCostCenterId>;
type EntityArrayResponseType = HttpResponse<IProjectCostCenterId[]>;

@Injectable({ providedIn: 'root' })
export class ProjectCostCenterIdService {
  public resourceUrl = SERVER_API_URL + 'api/project-cost-center-ids';

  constructor(protected http: HttpClient) {}

  create(projectCostCenterId: IProjectCostCenterId): Observable<EntityResponseType> {
    return this.http.post<IProjectCostCenterId>(this.resourceUrl, projectCostCenterId, { observe: 'response' });
  }

  update(projectCostCenterId: IProjectCostCenterId): Observable<EntityResponseType> {
    return this.http.put<IProjectCostCenterId>(this.resourceUrl, projectCostCenterId, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProjectCostCenterId>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProjectCostCenterId[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
