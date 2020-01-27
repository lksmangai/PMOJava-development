import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBacklogPractice } from 'app/shared/model/backlog-practice.model';

type EntityResponseType = HttpResponse<IBacklogPractice>;
type EntityArrayResponseType = HttpResponse<IBacklogPractice[]>;

@Injectable({ providedIn: 'root' })
export class BacklogPracticeService {
  public resourceUrl = SERVER_API_URL + 'api/backlog-practices';

  constructor(protected http: HttpClient) {}

  create(backlogPractice: IBacklogPractice): Observable<EntityResponseType> {
    return this.http.post<IBacklogPractice>(this.resourceUrl, backlogPractice, { observe: 'response' });
  }

  update(backlogPractice: IBacklogPractice): Observable<EntityResponseType> {
    return this.http.put<IBacklogPractice>(this.resourceUrl, backlogPractice, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBacklogPractice>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBacklogPractice[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
