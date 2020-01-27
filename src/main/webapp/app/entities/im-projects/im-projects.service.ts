import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IImProjects } from 'app/shared/model/im-projects.model';

type EntityResponseType = HttpResponse<IImProjects>;
type EntityArrayResponseType = HttpResponse<IImProjects[]>;

@Injectable({ providedIn: 'root' })
export class ImProjectsService {
  public resourceUrl = SERVER_API_URL + 'api/im-projects';

  constructor(protected http: HttpClient) {}

  create(imProjects: IImProjects): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(imProjects);
    return this.http
      .post<IImProjects>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(imProjects: IImProjects): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(imProjects);
    return this.http
      .put<IImProjects>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IImProjects>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IImProjects[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(imProjects: IImProjects): IImProjects {
    const copy: IImProjects = Object.assign({}, imProjects, {
      endDate: imProjects.endDate != null && imProjects.endDate.isValid() ? imProjects.endDate.toJSON() : null,
      startDate: imProjects.startDate != null && imProjects.startDate.isValid() ? imProjects.startDate.toJSON() : null,
      confirmDate: imProjects.confirmDate != null && imProjects.confirmDate.isValid() ? imProjects.confirmDate.toJSON() : null,
      costCacheDirty: imProjects.costCacheDirty != null && imProjects.costCacheDirty.isValid() ? imProjects.costCacheDirty.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.endDate = res.body.endDate != null ? moment(res.body.endDate) : null;
      res.body.startDate = res.body.startDate != null ? moment(res.body.startDate) : null;
      res.body.confirmDate = res.body.confirmDate != null ? moment(res.body.confirmDate) : null;
      res.body.costCacheDirty = res.body.costCacheDirty != null ? moment(res.body.costCacheDirty) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((imProjects: IImProjects) => {
        imProjects.endDate = imProjects.endDate != null ? moment(imProjects.endDate) : null;
        imProjects.startDate = imProjects.startDate != null ? moment(imProjects.startDate) : null;
        imProjects.confirmDate = imProjects.confirmDate != null ? moment(imProjects.confirmDate) : null;
        imProjects.costCacheDirty = imProjects.costCacheDirty != null ? moment(imProjects.costCacheDirty) : null;
      });
    }
    return res;
  }
}
