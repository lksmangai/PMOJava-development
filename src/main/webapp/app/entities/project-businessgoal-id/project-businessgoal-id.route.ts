import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProjectBusinessgoalId } from 'app/shared/model/project-businessgoal-id.model';
import { ProjectBusinessgoalIdService } from './project-businessgoal-id.service';
import { ProjectBusinessgoalIdComponent } from './project-businessgoal-id.component';
import { ProjectBusinessgoalIdDetailComponent } from './project-businessgoal-id-detail.component';
import { ProjectBusinessgoalIdUpdateComponent } from './project-businessgoal-id-update.component';
import { ProjectBusinessgoalIdDeletePopupComponent } from './project-businessgoal-id-delete-dialog.component';
import { IProjectBusinessgoalId } from 'app/shared/model/project-businessgoal-id.model';

@Injectable({ providedIn: 'root' })
export class ProjectBusinessgoalIdResolve implements Resolve<IProjectBusinessgoalId> {
  constructor(private service: ProjectBusinessgoalIdService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProjectBusinessgoalId> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ProjectBusinessgoalId>) => response.ok),
        map((projectBusinessgoalId: HttpResponse<ProjectBusinessgoalId>) => projectBusinessgoalId.body)
      );
    }
    return of(new ProjectBusinessgoalId());
  }
}

export const projectBusinessgoalIdRoute: Routes = [
  {
    path: '',
    component: ProjectBusinessgoalIdComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectBusinessgoalId.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProjectBusinessgoalIdDetailComponent,
    resolve: {
      projectBusinessgoalId: ProjectBusinessgoalIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectBusinessgoalId.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProjectBusinessgoalIdUpdateComponent,
    resolve: {
      projectBusinessgoalId: ProjectBusinessgoalIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectBusinessgoalId.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProjectBusinessgoalIdUpdateComponent,
    resolve: {
      projectBusinessgoalId: ProjectBusinessgoalIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectBusinessgoalId.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const projectBusinessgoalIdPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProjectBusinessgoalIdDeletePopupComponent,
    resolve: {
      projectBusinessgoalId: ProjectBusinessgoalIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectBusinessgoalId.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
