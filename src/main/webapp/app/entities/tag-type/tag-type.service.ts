import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITagType } from 'app/shared/model/tag-type.model';

type EntityResponseType = HttpResponse<ITagType>;
type EntityArrayResponseType = HttpResponse<ITagType[]>;

@Injectable({ providedIn: 'root' })
export class TagTypeService {
  public resourceUrl = SERVER_API_URL + 'api/tag-types';

  constructor(protected http: HttpClient) {}

  create(tagType: ITagType): Observable<EntityResponseType> {
    return this.http.post<ITagType>(this.resourceUrl, tagType, { observe: 'response' });
  }

  update(tagType: ITagType): Observable<EntityResponseType> {
    return this.http.put<ITagType>(this.resourceUrl, tagType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITagType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITagType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
