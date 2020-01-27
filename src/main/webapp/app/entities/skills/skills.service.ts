import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISkills } from 'app/shared/model/skills.model';

type EntityResponseType = HttpResponse<ISkills>;
type EntityArrayResponseType = HttpResponse<ISkills[]>;

@Injectable({ providedIn: 'root' })
export class SkillsService {
  public resourceUrl = SERVER_API_URL + 'api/skills';

  constructor(protected http: HttpClient) {}

  create(skills: ISkills): Observable<EntityResponseType> {
    return this.http.post<ISkills>(this.resourceUrl, skills, { observe: 'response' });
  }

  update(skills: ISkills): Observable<EntityResponseType> {
    return this.http.put<ISkills>(this.resourceUrl, skills, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISkills>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISkills[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
