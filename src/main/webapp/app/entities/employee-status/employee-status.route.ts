import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { EmployeeStatus } from 'app/shared/model/employee-status.model';
import { EmployeeStatusService } from './employee-status.service';
import { EmployeeStatusComponent } from './employee-status.component';
import { EmployeeStatusDetailComponent } from './employee-status-detail.component';
import { EmployeeStatusUpdateComponent } from './employee-status-update.component';
import { EmployeeStatusDeletePopupComponent } from './employee-status-delete-dialog.component';
import { IEmployeeStatus } from 'app/shared/model/employee-status.model';

@Injectable({ providedIn: 'root' })
export class EmployeeStatusResolve implements Resolve<IEmployeeStatus> {
  constructor(private service: EmployeeStatusService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEmployeeStatus> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<EmployeeStatus>) => response.ok),
        map((employeeStatus: HttpResponse<EmployeeStatus>) => employeeStatus.body)
      );
    }
    return of(new EmployeeStatus());
  }
}

export const employeeStatusRoute: Routes = [
  {
    path: '',
    component: EmployeeStatusComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.employeeStatus.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EmployeeStatusDetailComponent,
    resolve: {
      employeeStatus: EmployeeStatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.employeeStatus.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EmployeeStatusUpdateComponent,
    resolve: {
      employeeStatus: EmployeeStatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.employeeStatus.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EmployeeStatusUpdateComponent,
    resolve: {
      employeeStatus: EmployeeStatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.employeeStatus.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const employeeStatusPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: EmployeeStatusDeletePopupComponent,
    resolve: {
      employeeStatus: EmployeeStatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.employeeStatus.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
