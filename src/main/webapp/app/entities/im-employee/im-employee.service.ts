import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IImEmployee } from 'app/shared/model/im-employee.model';

type EntityResponseType = HttpResponse<IImEmployee>;
type EntityArrayResponseType = HttpResponse<IImEmployee[]>;

@Injectable({ providedIn: 'root' })
export class ImEmployeeService {
  public resourceUrl = SERVER_API_URL + 'api/im-employees';

  constructor(protected http: HttpClient) {}

  create(imEmployee: IImEmployee): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(imEmployee);
    return this.http
      .post<IImEmployee>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(imEmployee: IImEmployee): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(imEmployee);
    return this.http
      .put<IImEmployee>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IImEmployee>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IImEmployee[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(imEmployee: IImEmployee): IImEmployee {
    const copy: IImEmployee = Object.assign({}, imEmployee, {
      birthdate: imEmployee.birthdate != null && imEmployee.birthdate.isValid() ? imEmployee.birthdate.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.birthdate = res.body.birthdate != null ? moment(res.body.birthdate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((imEmployee: IImEmployee) => {
        imEmployee.birthdate = imEmployee.birthdate != null ? moment(imEmployee.birthdate) : null;
      });
    }
    return res;
  }
}
