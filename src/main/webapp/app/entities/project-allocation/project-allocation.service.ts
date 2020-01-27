import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProjectAllocation } from 'app/shared/model/project-allocation.model';

type EntityResponseType = HttpResponse<IProjectAllocation>;
type EntityArrayResponseType = HttpResponse<IProjectAllocation[]>;

@Injectable({ providedIn: 'root' })
export class ProjectAllocationService {
  public resourceUrl = SERVER_API_URL + 'api/project-allocations';

  constructor(protected http: HttpClient) {}

  create(projectAllocation: IProjectAllocation): Observable<EntityResponseType> {
    return this.http.post<IProjectAllocation>(this.resourceUrl, projectAllocation, { observe: 'response' });
  }

  update(projectAllocation: IProjectAllocation): Observable<EntityResponseType> {
    return this.http.put<IProjectAllocation>(this.resourceUrl, projectAllocation, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProjectAllocation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProjectAllocation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
