import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProjectTag } from 'app/shared/model/project-tag.model';
import { ProjectTagService } from './project-tag.service';
import { ProjectTagComponent } from './project-tag.component';
import { ProjectTagDetailComponent } from './project-tag-detail.component';
import { ProjectTagUpdateComponent } from './project-tag-update.component';
import { ProjectTagDeletePopupComponent } from './project-tag-delete-dialog.component';
import { IProjectTag } from 'app/shared/model/project-tag.model';

@Injectable({ providedIn: 'root' })
export class ProjectTagResolve implements Resolve<IProjectTag> {
  constructor(private service: ProjectTagService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProjectTag> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ProjectTag>) => response.ok),
        map((projectTag: HttpResponse<ProjectTag>) => projectTag.body)
      );
    }
    return of(new ProjectTag());
  }
}

export const projectTagRoute: Routes = [
  {
    path: '',
    component: ProjectTagComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectTag.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProjectTagDetailComponent,
    resolve: {
      projectTag: ProjectTagResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectTag.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProjectTagUpdateComponent,
    resolve: {
      projectTag: ProjectTagResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectTag.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProjectTagUpdateComponent,
    resolve: {
      projectTag: ProjectTagResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectTag.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const projectTagPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProjectTagDeletePopupComponent,
    resolve: {
      projectTag: ProjectTagResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectTag.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
