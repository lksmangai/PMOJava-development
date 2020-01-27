import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProjectTypeId } from 'app/shared/model/project-type-id.model';
import { ProjectTypeIdService } from './project-type-id.service';
import { ProjectTypeIdComponent } from './project-type-id.component';
import { ProjectTypeIdDetailComponent } from './project-type-id-detail.component';
import { ProjectTypeIdUpdateComponent } from './project-type-id-update.component';
import { ProjectTypeIdDeletePopupComponent } from './project-type-id-delete-dialog.component';
import { IProjectTypeId } from 'app/shared/model/project-type-id.model';

@Injectable({ providedIn: 'root' })
export class ProjectTypeIdResolve implements Resolve<IProjectTypeId> {
  constructor(private service: ProjectTypeIdService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProjectTypeId> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ProjectTypeId>) => response.ok),
        map((projectTypeId: HttpResponse<ProjectTypeId>) => projectTypeId.body)
      );
    }
    return of(new ProjectTypeId());
  }
}

export const projectTypeIdRoute: Routes = [
  {
    path: '',
    component: ProjectTypeIdComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectTypeId.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProjectTypeIdDetailComponent,
    resolve: {
      projectTypeId: ProjectTypeIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectTypeId.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProjectTypeIdUpdateComponent,
    resolve: {
      projectTypeId: ProjectTypeIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectTypeId.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProjectTypeIdUpdateComponent,
    resolve: {
      projectTypeId: ProjectTypeIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectTypeId.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const projectTypeIdPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProjectTypeIdDeletePopupComponent,
    resolve: {
      projectTypeId: ProjectTypeIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectTypeId.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
