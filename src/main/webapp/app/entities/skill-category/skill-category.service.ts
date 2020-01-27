import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISkillCategory } from 'app/shared/model/skill-category.model';

type EntityResponseType = HttpResponse<ISkillCategory>;
type EntityArrayResponseType = HttpResponse<ISkillCategory[]>;

@Injectable({ providedIn: 'root' })
export class SkillCategoryService {
  public resourceUrl = SERVER_API_URL + 'api/skill-categories';

  constructor(protected http: HttpClient) {}

  create(skillCategory: ISkillCategory): Observable<EntityResponseType> {
    return this.http.post<ISkillCategory>(this.resourceUrl, skillCategory, { observe: 'response' });
  }

  update(skillCategory: ISkillCategory): Observable<EntityResponseType> {
    return this.http.put<ISkillCategory>(this.resourceUrl, skillCategory, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISkillCategory>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISkillCategory[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
