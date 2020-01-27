import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { QnowUser } from 'app/shared/model/qnow-user.model';
import { QnowUserService } from './qnow-user.service';
import { QnowUserComponent } from './qnow-user.component';
import { QnowUserDetailComponent } from './qnow-user-detail.component';
import { QnowUserUpdateComponent } from './qnow-user-update.component';
import { QnowUserDeletePopupComponent } from './qnow-user-delete-dialog.component';
import { IQnowUser } from 'app/shared/model/qnow-user.model';

@Injectable({ providedIn: 'root' })
export class QnowUserResolve implements Resolve<IQnowUser> {
  constructor(private service: QnowUserService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IQnowUser> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<QnowUser>) => response.ok),
        map((qnowUser: HttpResponse<QnowUser>) => qnowUser.body)
      );
    }
    return of(new QnowUser());
  }
}

export const qnowUserRoute: Routes = [
  {
    path: '',
    component: QnowUserComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.qnowUser.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: QnowUserDetailComponent,
    resolve: {
      qnowUser: QnowUserResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.qnowUser.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: QnowUserUpdateComponent,
    resolve: {
      qnowUser: QnowUserResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.qnowUser.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: QnowUserUpdateComponent,
    resolve: {
      qnowUser: QnowUserResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.qnowUser.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const qnowUserPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: QnowUserDeletePopupComponent,
    resolve: {
      qnowUser: QnowUserResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.qnowUser.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
