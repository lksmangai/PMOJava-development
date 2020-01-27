import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ImProjects } from 'app/shared/model/im-projects.model';
import { ImProjectsService } from './im-projects.service';
import { ImProjectsComponent } from './im-projects.component';
import { ImProjectsDetailComponent } from './im-projects-detail.component';
import { ImProjectsUpdateComponent } from './im-projects-update.component';
import { ImProjectsDeletePopupComponent } from './im-projects-delete-dialog.component';
import { IImProjects } from 'app/shared/model/im-projects.model';

@Injectable({ providedIn: 'root' })
export class ImProjectsResolve implements Resolve<IImProjects> {
  constructor(private service: ImProjectsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IImProjects> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ImProjects>) => response.ok),
        map((imProjects: HttpResponse<ImProjects>) => imProjects.body)
      );
    }
    return of(new ImProjects());
  }
}

export const imProjectsRoute: Routes = [
  {
    path: '',
    component: ImProjectsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.imProjects.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ImProjectsDetailComponent,
    resolve: {
      imProjects: ImProjectsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.imProjects.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ImProjectsUpdateComponent,
    resolve: {
      imProjects: ImProjectsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.imProjects.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ImProjectsUpdateComponent,
    resolve: {
      imProjects: ImProjectsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.imProjects.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const imProjectsPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ImProjectsDeletePopupComponent,
    resolve: {
      imProjects: ImProjectsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.imProjects.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
