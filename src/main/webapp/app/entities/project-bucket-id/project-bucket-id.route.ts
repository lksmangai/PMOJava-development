import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProjectBucketId } from 'app/shared/model/project-bucket-id.model';
import { ProjectBucketIdService } from './project-bucket-id.service';
import { ProjectBucketIdComponent } from './project-bucket-id.component';
import { ProjectBucketIdDetailComponent } from './project-bucket-id-detail.component';
import { ProjectBucketIdUpdateComponent } from './project-bucket-id-update.component';
import { ProjectBucketIdDeletePopupComponent } from './project-bucket-id-delete-dialog.component';
import { IProjectBucketId } from 'app/shared/model/project-bucket-id.model';

@Injectable({ providedIn: 'root' })
export class ProjectBucketIdResolve implements Resolve<IProjectBucketId> {
  constructor(private service: ProjectBucketIdService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProjectBucketId> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ProjectBucketId>) => response.ok),
        map((projectBucketId: HttpResponse<ProjectBucketId>) => projectBucketId.body)
      );
    }
    return of(new ProjectBucketId());
  }
}

export const projectBucketIdRoute: Routes = [
  {
    path: '',
    component: ProjectBucketIdComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectBucketId.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProjectBucketIdDetailComponent,
    resolve: {
      projectBucketId: ProjectBucketIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectBucketId.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProjectBucketIdUpdateComponent,
    resolve: {
      projectBucketId: ProjectBucketIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectBucketId.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProjectBucketIdUpdateComponent,
    resolve: {
      projectBucketId: ProjectBucketIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectBucketId.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const projectBucketIdPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProjectBucketIdDeletePopupComponent,
    resolve: {
      projectBucketId: ProjectBucketIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectBucketId.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
