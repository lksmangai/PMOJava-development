import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SkillCategory } from 'app/shared/model/skill-category.model';
import { SkillCategoryService } from './skill-category.service';
import { SkillCategoryComponent } from './skill-category.component';
import { SkillCategoryDetailComponent } from './skill-category-detail.component';
import { SkillCategoryUpdateComponent } from './skill-category-update.component';
import { SkillCategoryDeletePopupComponent } from './skill-category-delete-dialog.component';
import { ISkillCategory } from 'app/shared/model/skill-category.model';

@Injectable({ providedIn: 'root' })
export class SkillCategoryResolve implements Resolve<ISkillCategory> {
  constructor(private service: SkillCategoryService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISkillCategory> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<SkillCategory>) => response.ok),
        map((skillCategory: HttpResponse<SkillCategory>) => skillCategory.body)
      );
    }
    return of(new SkillCategory());
  }
}

export const skillCategoryRoute: Routes = [
  {
    path: '',
    component: SkillCategoryComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.skillCategory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SkillCategoryDetailComponent,
    resolve: {
      skillCategory: SkillCategoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.skillCategory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SkillCategoryUpdateComponent,
    resolve: {
      skillCategory: SkillCategoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.skillCategory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SkillCategoryUpdateComponent,
    resolve: {
      skillCategory: SkillCategoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.skillCategory.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const skillCategoryPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SkillCategoryDeletePopupComponent,
    resolve: {
      skillCategory: SkillCategoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.skillCategory.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
