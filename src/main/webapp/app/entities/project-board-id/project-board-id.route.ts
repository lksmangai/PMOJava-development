import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProjectBoardId } from 'app/shared/model/project-board-id.model';
import { ProjectBoardIdService } from './project-board-id.service';
import { ProjectBoardIdComponent } from './project-board-id.component';
import { ProjectBoardIdDetailComponent } from './project-board-id-detail.component';
import { ProjectBoardIdUpdateComponent } from './project-board-id-update.component';
import { ProjectBoardIdDeletePopupComponent } from './project-board-id-delete-dialog.component';
import { IProjectBoardId } from 'app/shared/model/project-board-id.model';

@Injectable({ providedIn: 'root' })
export class ProjectBoardIdResolve implements Resolve<IProjectBoardId> {
  constructor(private service: ProjectBoardIdService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProjectBoardId> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ProjectBoardId>) => response.ok),
        map((projectBoardId: HttpResponse<ProjectBoardId>) => projectBoardId.body)
      );
    }
    return of(new ProjectBoardId());
  }
}

export const projectBoardIdRoute: Routes = [
  {
    path: '',
    component: ProjectBoardIdComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectBoardId.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProjectBoardIdDetailComponent,
    resolve: {
      projectBoardId: ProjectBoardIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectBoardId.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProjectBoardIdUpdateComponent,
    resolve: {
      projectBoardId: ProjectBoardIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectBoardId.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProjectBoardIdUpdateComponent,
    resolve: {
      projectBoardId: ProjectBoardIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectBoardId.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const projectBoardIdPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProjectBoardIdDeletePopupComponent,
    resolve: {
      projectBoardId: ProjectBoardIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectBoardId.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
