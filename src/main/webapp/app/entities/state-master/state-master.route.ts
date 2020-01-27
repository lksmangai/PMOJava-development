import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StateMaster } from 'app/shared/model/state-master.model';
import { StateMasterService } from './state-master.service';
import { StateMasterComponent } from './state-master.component';
import { StateMasterDetailComponent } from './state-master-detail.component';
import { StateMasterUpdateComponent } from './state-master-update.component';
import { StateMasterDeletePopupComponent } from './state-master-delete-dialog.component';
import { IStateMaster } from 'app/shared/model/state-master.model';

@Injectable({ providedIn: 'root' })
export class StateMasterResolve implements Resolve<IStateMaster> {
  constructor(private service: StateMasterService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IStateMaster> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<StateMaster>) => response.ok),
        map((stateMaster: HttpResponse<StateMaster>) => stateMaster.body)
      );
    }
    return of(new StateMaster());
  }
}

export const stateMasterRoute: Routes = [
  {
    path: '',
    component: StateMasterComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.stateMaster.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: StateMasterDetailComponent,
    resolve: {
      stateMaster: StateMasterResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.stateMaster.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: StateMasterUpdateComponent,
    resolve: {
      stateMaster: StateMasterResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.stateMaster.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: StateMasterUpdateComponent,
    resolve: {
      stateMaster: StateMasterResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.stateMaster.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const stateMasterPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: StateMasterDeletePopupComponent,
    resolve: {
      stateMaster: StateMasterResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.stateMaster.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
