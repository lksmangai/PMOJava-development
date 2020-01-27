import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProjectSubgoalId } from 'app/shared/model/project-subgoal-id.model';
import { ProjectSubgoalIdService } from './project-subgoal-id.service';
import { ProjectSubgoalIdComponent } from './project-subgoal-id.component';
import { ProjectSubgoalIdDetailComponent } from './project-subgoal-id-detail.component';
import { ProjectSubgoalIdUpdateComponent } from './project-subgoal-id-update.component';
import { ProjectSubgoalIdDeletePopupComponent } from './project-subgoal-id-delete-dialog.component';
import { IProjectSubgoalId } from 'app/shared/model/project-subgoal-id.model';

@Injectable({ providedIn: 'root' })
export class ProjectSubgoalIdResolve implements Resolve<IProjectSubgoalId> {
  constructor(private service: ProjectSubgoalIdService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProjectSubgoalId> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ProjectSubgoalId>) => response.ok),
        map((projectSubgoalId: HttpResponse<ProjectSubgoalId>) => projectSubgoalId.body)
      );
    }
    return of(new ProjectSubgoalId());
  }
}

export const projectSubgoalIdRoute: Routes = [
  {
    path: '',
    component: ProjectSubgoalIdComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectSubgoalId.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProjectSubgoalIdDetailComponent,
    resolve: {
      projectSubgoalId: ProjectSubgoalIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectSubgoalId.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProjectSubgoalIdUpdateComponent,
    resolve: {
      projectSubgoalId: ProjectSubgoalIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectSubgoalId.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProjectSubgoalIdUpdateComponent,
    resolve: {
      projectSubgoalId: ProjectSubgoalIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectSubgoalId.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const projectSubgoalIdPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProjectSubgoalIdDeletePopupComponent,
    resolve: {
      projectSubgoalId: ProjectSubgoalIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectSubgoalId.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
