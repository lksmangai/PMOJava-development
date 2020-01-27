import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProjectInitiativeId } from 'app/shared/model/project-initiative-id.model';
import { ProjectInitiativeIdService } from './project-initiative-id.service';
import { ProjectInitiativeIdComponent } from './project-initiative-id.component';
import { ProjectInitiativeIdDetailComponent } from './project-initiative-id-detail.component';
import { ProjectInitiativeIdUpdateComponent } from './project-initiative-id-update.component';
import { ProjectInitiativeIdDeletePopupComponent } from './project-initiative-id-delete-dialog.component';
import { IProjectInitiativeId } from 'app/shared/model/project-initiative-id.model';

@Injectable({ providedIn: 'root' })
export class ProjectInitiativeIdResolve implements Resolve<IProjectInitiativeId> {
  constructor(private service: ProjectInitiativeIdService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProjectInitiativeId> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ProjectInitiativeId>) => response.ok),
        map((projectInitiativeId: HttpResponse<ProjectInitiativeId>) => projectInitiativeId.body)
      );
    }
    return of(new ProjectInitiativeId());
  }
}

export const projectInitiativeIdRoute: Routes = [
  {
    path: '',
    component: ProjectInitiativeIdComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectInitiativeId.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProjectInitiativeIdDetailComponent,
    resolve: {
      projectInitiativeId: ProjectInitiativeIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectInitiativeId.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProjectInitiativeIdUpdateComponent,
    resolve: {
      projectInitiativeId: ProjectInitiativeIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectInitiativeId.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProjectInitiativeIdUpdateComponent,
    resolve: {
      projectInitiativeId: ProjectInitiativeIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectInitiativeId.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const projectInitiativeIdPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProjectInitiativeIdDeletePopupComponent,
    resolve: {
      projectInitiativeId: ProjectInitiativeIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectInitiativeId.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
