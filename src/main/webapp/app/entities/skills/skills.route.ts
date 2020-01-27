import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Skills } from 'app/shared/model/skills.model';
import { SkillsService } from './skills.service';
import { SkillsComponent } from './skills.component';
import { SkillsDetailComponent } from './skills-detail.component';
import { SkillsUpdateComponent } from './skills-update.component';
import { SkillsDeletePopupComponent } from './skills-delete-dialog.component';
import { ISkills } from 'app/shared/model/skills.model';

@Injectable({ providedIn: 'root' })
export class SkillsResolve implements Resolve<ISkills> {
  constructor(private service: SkillsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISkills> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Skills>) => response.ok),
        map((skills: HttpResponse<Skills>) => skills.body)
      );
    }
    return of(new Skills());
  }
}

export const skillsRoute: Routes = [
  {
    path: '',
    component: SkillsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.skills.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SkillsDetailComponent,
    resolve: {
      skills: SkillsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.skills.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SkillsUpdateComponent,
    resolve: {
      skills: SkillsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.skills.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SkillsUpdateComponent,
    resolve: {
      skills: SkillsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.skills.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const skillsPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SkillsDeletePopupComponent,
    resolve: {
      skills: SkillsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.skills.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
