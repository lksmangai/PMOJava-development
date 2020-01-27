import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SkillTable } from 'app/shared/model/skill-table.model';
import { SkillTableService } from './skill-table.service';
import { SkillTableComponent } from './skill-table.component';
import { SkillTableDetailComponent } from './skill-table-detail.component';
import { SkillTableUpdateComponent } from './skill-table-update.component';
import { SkillTableDeletePopupComponent } from './skill-table-delete-dialog.component';
import { ISkillTable } from 'app/shared/model/skill-table.model';

@Injectable({ providedIn: 'root' })
export class SkillTableResolve implements Resolve<ISkillTable> {
  constructor(private service: SkillTableService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISkillTable> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<SkillTable>) => response.ok),
        map((skillTable: HttpResponse<SkillTable>) => skillTable.body)
      );
    }
    return of(new SkillTable());
  }
}

export const skillTableRoute: Routes = [
  {
    path: '',
    component: SkillTableComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.skillTable.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SkillTableDetailComponent,
    resolve: {
      skillTable: SkillTableResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.skillTable.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SkillTableUpdateComponent,
    resolve: {
      skillTable: SkillTableResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.skillTable.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SkillTableUpdateComponent,
    resolve: {
      skillTable: SkillTableResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.skillTable.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const skillTablePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SkillTableDeletePopupComponent,
    resolve: {
      skillTable: SkillTableResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.skillTable.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
