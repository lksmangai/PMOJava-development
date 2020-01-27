import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProjectClass } from 'app/shared/model/project-class.model';
import { ProjectClassService } from './project-class.service';
import { ProjectClassComponent } from './project-class.component';
import { ProjectClassDetailComponent } from './project-class-detail.component';
import { ProjectClassUpdateComponent } from './project-class-update.component';
import { ProjectClassDeletePopupComponent } from './project-class-delete-dialog.component';
import { IProjectClass } from 'app/shared/model/project-class.model';

@Injectable({ providedIn: 'root' })
export class ProjectClassResolve implements Resolve<IProjectClass> {
  constructor(private service: ProjectClassService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProjectClass> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ProjectClass>) => response.ok),
        map((projectClass: HttpResponse<ProjectClass>) => projectClass.body)
      );
    }
    return of(new ProjectClass());
  }
}

export const projectClassRoute: Routes = [
  {
    path: '',
    component: ProjectClassComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectClass.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProjectClassDetailComponent,
    resolve: {
      projectClass: ProjectClassResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectClass.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProjectClassUpdateComponent,
    resolve: {
      projectClass: ProjectClassResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectClass.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProjectClassUpdateComponent,
    resolve: {
      projectClass: ProjectClassResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectClass.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const projectClassPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProjectClassDeletePopupComponent,
    resolve: {
      projectClass: ProjectClassResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectClass.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
