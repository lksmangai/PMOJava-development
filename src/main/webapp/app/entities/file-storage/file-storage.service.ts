import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFileStorage } from 'app/shared/model/file-storage.model';

type EntityResponseType = HttpResponse<IFileStorage>;
type EntityArrayResponseType = HttpResponse<IFileStorage[]>;

@Injectable({ providedIn: 'root' })
export class FileStorageService {
  public resourceUrl = SERVER_API_URL + 'api/file-storages';

  constructor(protected http: HttpClient) {}

  create(fileStorage: IFileStorage): Observable<EntityResponseType> {
    return this.http.post<IFileStorage>(this.resourceUrl, fileStorage, { observe: 'response' });
  }

  update(fileStorage: IFileStorage): Observable<EntityResponseType> {
    return this.http.put<IFileStorage>(this.resourceUrl, fileStorage, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFileStorage>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFileStorage[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
