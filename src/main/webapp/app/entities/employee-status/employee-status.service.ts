import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEmployeeStatus } from 'app/shared/model/employee-status.model';

type EntityResponseType = HttpResponse<IEmployeeStatus>;
type EntityArrayResponseType = HttpResponse<IEmployeeStatus[]>;

@Injectable({ providedIn: 'root' })
export class EmployeeStatusService {
  public resourceUrl = SERVER_API_URL + 'api/employee-statuses';

  constructor(protected http: HttpClient) {}

  create(employeeStatus: IEmployeeStatus): Observable<EntityResponseType> {
    return this.http.post<IEmployeeStatus>(this.resourceUrl, employeeStatus, { observe: 'response' });
  }

  update(employeeStatus: IEmployeeStatus): Observable<EntityResponseType> {
    return this.http.put<IEmployeeStatus>(this.resourceUrl, employeeStatus, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEmployeeStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEmployeeStatus[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
