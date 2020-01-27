import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProjectStatusId } from 'app/shared/model/project-status-id.model';
import { ProjectStatusIdService } from './project-status-id.service';
import { ProjectStatusIdComponent } from './project-status-id.component';
import { ProjectStatusIdDetailComponent } from './project-status-id-detail.component';
import { ProjectStatusIdUpdateComponent } from './project-status-id-update.component';
import { ProjectStatusIdDeletePopupComponent } from './project-status-id-delete-dialog.component';
import { IProjectStatusId } from 'app/shared/model/project-status-id.model';

@Injectable({ providedIn: 'root' })
export class ProjectStatusIdResolve implements Resolve<IProjectStatusId> {
  constructor(private service: ProjectStatusIdService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProjectStatusId> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ProjectStatusId>) => response.ok),
        map((projectStatusId: HttpResponse<ProjectStatusId>) => projectStatusId.body)
      );
    }
    return of(new ProjectStatusId());
  }
}

export const projectStatusIdRoute: Routes = [
  {
    path: '',
    component: ProjectStatusIdComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectStatusId.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProjectStatusIdDetailComponent,
    resolve: {
      projectStatusId: ProjectStatusIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectStatusId.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProjectStatusIdUpdateComponent,
    resolve: {
      projectStatusId: ProjectStatusIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectStatusId.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProjectStatusIdUpdateComponent,
    resolve: {
      projectStatusId: ProjectStatusIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectStatusId.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const projectStatusIdPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProjectStatusIdDeletePopupComponent,
    resolve: {
      projectStatusId: ProjectStatusIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectStatusId.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
