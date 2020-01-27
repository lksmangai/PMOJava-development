import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISkillExpertise } from 'app/shared/model/skill-expertise.model';

type EntityResponseType = HttpResponse<ISkillExpertise>;
type EntityArrayResponseType = HttpResponse<ISkillExpertise[]>;

@Injectable({ providedIn: 'root' })
export class SkillExpertiseService {
  public resourceUrl = SERVER_API_URL + 'api/skill-expertises';

  constructor(protected http: HttpClient) {}

  create(skillExpertise: ISkillExpertise): Observable<EntityResponseType> {
    return this.http.post<ISkillExpertise>(this.resourceUrl, skillExpertise, { observe: 'response' });
  }

  update(skillExpertise: ISkillExpertise): Observable<EntityResponseType> {
    return this.http.put<ISkillExpertise>(this.resourceUrl, skillExpertise, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISkillExpertise>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISkillExpertise[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
