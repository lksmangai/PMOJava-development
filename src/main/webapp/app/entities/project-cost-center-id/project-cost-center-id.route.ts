import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProjectCostCenterId } from 'app/shared/model/project-cost-center-id.model';
import { ProjectCostCenterIdService } from './project-cost-center-id.service';
import { ProjectCostCenterIdComponent } from './project-cost-center-id.component';
import { ProjectCostCenterIdDetailComponent } from './project-cost-center-id-detail.component';
import { ProjectCostCenterIdUpdateComponent } from './project-cost-center-id-update.component';
import { ProjectCostCenterIdDeletePopupComponent } from './project-cost-center-id-delete-dialog.component';
import { IProjectCostCenterId } from 'app/shared/model/project-cost-center-id.model';

@Injectable({ providedIn: 'root' })
export class ProjectCostCenterIdResolve implements Resolve<IProjectCostCenterId> {
  constructor(private service: ProjectCostCenterIdService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProjectCostCenterId> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ProjectCostCenterId>) => response.ok),
        map((projectCostCenterId: HttpResponse<ProjectCostCenterId>) => projectCostCenterId.body)
      );
    }
    return of(new ProjectCostCenterId());
  }
}

export const projectCostCenterIdRoute: Routes = [
  {
    path: '',
    component: ProjectCostCenterIdComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectCostCenterId.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProjectCostCenterIdDetailComponent,
    resolve: {
      projectCostCenterId: ProjectCostCenterIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectCostCenterId.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProjectCostCenterIdUpdateComponent,
    resolve: {
      projectCostCenterId: ProjectCostCenterIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectCostCenterId.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProjectCostCenterIdUpdateComponent,
    resolve: {
      projectCostCenterId: ProjectCostCenterIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectCostCenterId.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const projectCostCenterIdPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProjectCostCenterIdDeletePopupComponent,
    resolve: {
      projectCostCenterId: ProjectCostCenterIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectCostCenterId.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
