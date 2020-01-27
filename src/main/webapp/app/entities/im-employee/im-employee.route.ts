import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ImEmployee } from 'app/shared/model/im-employee.model';
import { ImEmployeeService } from './im-employee.service';
import { ImEmployeeComponent } from './im-employee.component';
import { ImEmployeeDetailComponent } from './im-employee-detail.component';
import { ImEmployeeUpdateComponent } from './im-employee-update.component';
import { ImEmployeeDeletePopupComponent } from './im-employee-delete-dialog.component';
import { IImEmployee } from 'app/shared/model/im-employee.model';

@Injectable({ providedIn: 'root' })
export class ImEmployeeResolve implements Resolve<IImEmployee> {
  constructor(private service: ImEmployeeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IImEmployee> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ImEmployee>) => response.ok),
        map((imEmployee: HttpResponse<ImEmployee>) => imEmployee.body)
      );
    }
    return of(new ImEmployee());
  }
}

export const imEmployeeRoute: Routes = [
  {
    path: '',
    component: ImEmployeeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.imEmployee.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ImEmployeeDetailComponent,
    resolve: {
      imEmployee: ImEmployeeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.imEmployee.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ImEmployeeUpdateComponent,
    resolve: {
      imEmployee: ImEmployeeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.imEmployee.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ImEmployeeUpdateComponent,
    resolve: {
      imEmployee: ImEmployeeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.imEmployee.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const imEmployeePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ImEmployeeDeletePopupComponent,
    resolve: {
      imEmployee: ImEmployeeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.imEmployee.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
