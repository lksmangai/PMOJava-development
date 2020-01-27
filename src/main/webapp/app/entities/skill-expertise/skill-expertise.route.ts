import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SkillExpertise } from 'app/shared/model/skill-expertise.model';
import { SkillExpertiseService } from './skill-expertise.service';
import { SkillExpertiseComponent } from './skill-expertise.component';
import { SkillExpertiseDetailComponent } from './skill-expertise-detail.component';
import { SkillExpertiseUpdateComponent } from './skill-expertise-update.component';
import { SkillExpertiseDeletePopupComponent } from './skill-expertise-delete-dialog.component';
import { ISkillExpertise } from 'app/shared/model/skill-expertise.model';

@Injectable({ providedIn: 'root' })
export class SkillExpertiseResolve implements Resolve<ISkillExpertise> {
  constructor(private service: SkillExpertiseService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISkillExpertise> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<SkillExpertise>) => response.ok),
        map((skillExpertise: HttpResponse<SkillExpertise>) => skillExpertise.body)
      );
    }
    return of(new SkillExpertise());
  }
}

export const skillExpertiseRoute: Routes = [
  {
    path: '',
    component: SkillExpertiseComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.skillExpertise.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SkillExpertiseDetailComponent,
    resolve: {
      skillExpertise: SkillExpertiseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.skillExpertise.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SkillExpertiseUpdateComponent,
    resolve: {
      skillExpertise: SkillExpertiseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.skillExpertise.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SkillExpertiseUpdateComponent,
    resolve: {
      skillExpertise: SkillExpertiseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.skillExpertise.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const skillExpertisePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SkillExpertiseDeletePopupComponent,
    resolve: {
      skillExpertise: SkillExpertiseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.skillExpertise.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
