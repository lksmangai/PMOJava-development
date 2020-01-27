import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { BacklogPractice } from 'app/shared/model/backlog-practice.model';
import { BacklogPracticeService } from './backlog-practice.service';
import { BacklogPracticeComponent } from './backlog-practice.component';
import { BacklogPracticeDetailComponent } from './backlog-practice-detail.component';
import { BacklogPracticeUpdateComponent } from './backlog-practice-update.component';
import { BacklogPracticeDeletePopupComponent } from './backlog-practice-delete-dialog.component';
import { IBacklogPractice } from 'app/shared/model/backlog-practice.model';

@Injectable({ providedIn: 'root' })
export class BacklogPracticeResolve implements Resolve<IBacklogPractice> {
  constructor(private service: BacklogPracticeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IBacklogPractice> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<BacklogPractice>) => response.ok),
        map((backlogPractice: HttpResponse<BacklogPractice>) => backlogPractice.body)
      );
    }
    return of(new BacklogPractice());
  }
}

export const backlogPracticeRoute: Routes = [
  {
    path: '',
    component: BacklogPracticeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.backlogPractice.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BacklogPracticeDetailComponent,
    resolve: {
      backlogPractice: BacklogPracticeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.backlogPractice.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BacklogPracticeUpdateComponent,
    resolve: {
      backlogPractice: BacklogPracticeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.backlogPractice.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BacklogPracticeUpdateComponent,
    resolve: {
      backlogPractice: BacklogPracticeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.backlogPractice.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const backlogPracticePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: BacklogPracticeDeletePopupComponent,
    resolve: {
      backlogPractice: BacklogPracticeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.backlogPractice.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
