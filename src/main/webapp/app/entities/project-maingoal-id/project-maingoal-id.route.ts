import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProjectMaingoalId } from 'app/shared/model/project-maingoal-id.model';
import { ProjectMaingoalIdService } from './project-maingoal-id.service';
import { ProjectMaingoalIdComponent } from './project-maingoal-id.component';
import { ProjectMaingoalIdDetailComponent } from './project-maingoal-id-detail.component';
import { ProjectMaingoalIdUpdateComponent } from './project-maingoal-id-update.component';
import { ProjectMaingoalIdDeletePopupComponent } from './project-maingoal-id-delete-dialog.component';
import { IProjectMaingoalId } from 'app/shared/model/project-maingoal-id.model';

@Injectable({ providedIn: 'root' })
export class ProjectMaingoalIdResolve implements Resolve<IProjectMaingoalId> {
  constructor(private service: ProjectMaingoalIdService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProjectMaingoalId> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ProjectMaingoalId>) => response.ok),
        map((projectMaingoalId: HttpResponse<ProjectMaingoalId>) => projectMaingoalId.body)
      );
    }
    return of(new ProjectMaingoalId());
  }
}

export const projectMaingoalIdRoute: Routes = [
  {
    path: '',
    component: ProjectMaingoalIdComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectMaingoalId.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProjectMaingoalIdDetailComponent,
    resolve: {
      projectMaingoalId: ProjectMaingoalIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectMaingoalId.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProjectMaingoalIdUpdateComponent,
    resolve: {
      projectMaingoalId: ProjectMaingoalIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectMaingoalId.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProjectMaingoalIdUpdateComponent,
    resolve: {
      projectMaingoalId: ProjectMaingoalIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectMaingoalId.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const projectMaingoalIdPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProjectMaingoalIdDeletePopupComponent,
    resolve: {
      projectMaingoalId: ProjectMaingoalIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectMaingoalId.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
