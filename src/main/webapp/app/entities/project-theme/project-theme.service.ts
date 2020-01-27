import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProjectTheme } from 'app/shared/model/project-theme.model';

type EntityResponseType = HttpResponse<IProjectTheme>;
type EntityArrayResponseType = HttpResponse<IProjectTheme[]>;

@Injectable({ providedIn: 'root' })
export class ProjectThemeService {
  public resourceUrl = SERVER_API_URL + 'api/project-themes';

  constructor(protected http: HttpClient) {}

  create(projectTheme: IProjectTheme): Observable<EntityResponseType> {
    return this.http.post<IProjectTheme>(this.resourceUrl, projectTheme, { observe: 'response' });
  }

  update(projectTheme: IProjectTheme): Observable<EntityResponseType> {
    return this.http.put<IProjectTheme>(this.resourceUrl, projectTheme, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProjectTheme>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProjectTheme[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
