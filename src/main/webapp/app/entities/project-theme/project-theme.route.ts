import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProjectTheme } from 'app/shared/model/project-theme.model';
import { ProjectThemeService } from './project-theme.service';
import { ProjectThemeComponent } from './project-theme.component';
import { ProjectThemeDetailComponent } from './project-theme-detail.component';
import { ProjectThemeUpdateComponent } from './project-theme-update.component';
import { ProjectThemeDeletePopupComponent } from './project-theme-delete-dialog.component';
import { IProjectTheme } from 'app/shared/model/project-theme.model';

@Injectable({ providedIn: 'root' })
export class ProjectThemeResolve implements Resolve<IProjectTheme> {
  constructor(private service: ProjectThemeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProjectTheme> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ProjectTheme>) => response.ok),
        map((projectTheme: HttpResponse<ProjectTheme>) => projectTheme.body)
      );
    }
    return of(new ProjectTheme());
  }
}

export const projectThemeRoute: Routes = [
  {
    path: '',
    component: ProjectThemeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectTheme.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProjectThemeDetailComponent,
    resolve: {
      projectTheme: ProjectThemeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectTheme.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProjectThemeUpdateComponent,
    resolve: {
      projectTheme: ProjectThemeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectTheme.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProjectThemeUpdateComponent,
    resolve: {
      projectTheme: ProjectThemeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectTheme.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const projectThemePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProjectThemeDeletePopupComponent,
    resolve: {
      projectTheme: ProjectThemeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectTheme.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
