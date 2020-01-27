import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ImTimesheet } from 'app/shared/model/im-timesheet.model';
import { ImTimesheetService } from './im-timesheet.service';
import { ImTimesheetComponent } from './im-timesheet.component';
import { ImTimesheetDetailComponent } from './im-timesheet-detail.component';
import { ImTimesheetUpdateComponent } from './im-timesheet-update.component';
import { ImTimesheetDeletePopupComponent } from './im-timesheet-delete-dialog.component';
import { IImTimesheet } from 'app/shared/model/im-timesheet.model';

@Injectable({ providedIn: 'root' })
export class ImTimesheetResolve implements Resolve<IImTimesheet> {
  constructor(private service: ImTimesheetService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IImTimesheet> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ImTimesheet>) => response.ok),
        map((imTimesheet: HttpResponse<ImTimesheet>) => imTimesheet.body)
      );
    }
    return of(new ImTimesheet());
  }
}

export const imTimesheetRoute: Routes = [
  {
    path: '',
    component: ImTimesheetComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.imTimesheet.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ImTimesheetDetailComponent,
    resolve: {
      imTimesheet: ImTimesheetResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.imTimesheet.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ImTimesheetUpdateComponent,
    resolve: {
      imTimesheet: ImTimesheetResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.imTimesheet.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ImTimesheetUpdateComponent,
    resolve: {
      imTimesheet: ImTimesheetResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.imTimesheet.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const imTimesheetPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ImTimesheetDeletePopupComponent,
    resolve: {
      imTimesheet: ImTimesheetResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.imTimesheet.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
