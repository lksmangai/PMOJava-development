import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProjectVertical } from 'app/shared/model/project-vertical.model';
import { ProjectVerticalService } from './project-vertical.service';
import { ProjectVerticalComponent } from './project-vertical.component';
import { ProjectVerticalDetailComponent } from './project-vertical-detail.component';
import { ProjectVerticalUpdateComponent } from './project-vertical-update.component';
import { ProjectVerticalDeletePopupComponent } from './project-vertical-delete-dialog.component';
import { IProjectVertical } from 'app/shared/model/project-vertical.model';

@Injectable({ providedIn: 'root' })
export class ProjectVerticalResolve implements Resolve<IProjectVertical> {
  constructor(private service: ProjectVerticalService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProjectVertical> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ProjectVertical>) => response.ok),
        map((projectVertical: HttpResponse<ProjectVertical>) => projectVertical.body)
      );
    }
    return of(new ProjectVertical());
  }
}

export const projectVerticalRoute: Routes = [
  {
    path: '',
    component: ProjectVerticalComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectVertical.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProjectVerticalDetailComponent,
    resolve: {
      projectVertical: ProjectVerticalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectVertical.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProjectVerticalUpdateComponent,
    resolve: {
      projectVertical: ProjectVerticalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectVertical.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProjectVerticalUpdateComponent,
    resolve: {
      projectVertical: ProjectVerticalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectVertical.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const projectVerticalPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProjectVerticalDeletePopupComponent,
    resolve: {
      projectVertical: ProjectVerticalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectVertical.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
