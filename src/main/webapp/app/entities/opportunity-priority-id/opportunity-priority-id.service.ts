import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOpportunityPriorityId } from 'app/shared/model/opportunity-priority-id.model';

type EntityResponseType = HttpResponse<IOpportunityPriorityId>;
type EntityArrayResponseType = HttpResponse<IOpportunityPriorityId[]>;

@Injectable({ providedIn: 'root' })
export class OpportunityPriorityIdService {
  public resourceUrl = SERVER_API_URL + 'api/opportunity-priority-ids';

  constructor(protected http: HttpClient) {}

  create(opportunityPriorityId: IOpportunityPriorityId): Observable<EntityResponseType> {
    return this.http.post<IOpportunityPriorityId>(this.resourceUrl, opportunityPriorityId, { observe: 'response' });
  }

  update(opportunityPriorityId: IOpportunityPriorityId): Observable<EntityResponseType> {
    return this.http.put<IOpportunityPriorityId>(this.resourceUrl, opportunityPriorityId, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOpportunityPriorityId>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOpportunityPriorityId[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
