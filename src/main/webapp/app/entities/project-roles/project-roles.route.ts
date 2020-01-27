import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProjectRoles } from 'app/shared/model/project-roles.model';
import { ProjectRolesService } from './project-roles.service';
import { ProjectRolesComponent } from './project-roles.component';
import { ProjectRolesDetailComponent } from './project-roles-detail.component';
import { ProjectRolesUpdateComponent } from './project-roles-update.component';
import { ProjectRolesDeletePopupComponent } from './project-roles-delete-dialog.component';
import { IProjectRoles } from 'app/shared/model/project-roles.model';

@Injectable({ providedIn: 'root' })
export class ProjectRolesResolve implements Resolve<IProjectRoles> {
  constructor(private service: ProjectRolesService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProjectRoles> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ProjectRoles>) => response.ok),
        map((projectRoles: HttpResponse<ProjectRoles>) => projectRoles.body)
      );
    }
    return of(new ProjectRoles());
  }
}

export const projectRolesRoute: Routes = [
  {
    path: '',
    component: ProjectRolesComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectRoles.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProjectRolesDetailComponent,
    resolve: {
      projectRoles: ProjectRolesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectRoles.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProjectRolesUpdateComponent,
    resolve: {
      projectRoles: ProjectRolesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectRoles.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProjectRolesUpdateComponent,
    resolve: {
      projectRoles: ProjectRolesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectRoles.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const projectRolesPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProjectRolesDeletePopupComponent,
    resolve: {
      projectRoles: ProjectRolesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectRoles.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
