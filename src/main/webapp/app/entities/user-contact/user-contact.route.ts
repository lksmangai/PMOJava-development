import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { UserContact } from 'app/shared/model/user-contact.model';
import { UserContactService } from './user-contact.service';
import { UserContactComponent } from './user-contact.component';
import { UserContactDetailComponent } from './user-contact-detail.component';
import { UserContactUpdateComponent } from './user-contact-update.component';
import { UserContactDeletePopupComponent } from './user-contact-delete-dialog.component';
import { IUserContact } from 'app/shared/model/user-contact.model';

@Injectable({ providedIn: 'root' })
export class UserContactResolve implements Resolve<IUserContact> {
  constructor(private service: UserContactService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IUserContact> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<UserContact>) => response.ok),
        map((userContact: HttpResponse<UserContact>) => userContact.body)
      );
    }
    return of(new UserContact());
  }
}

export const userContactRoute: Routes = [
  {
    path: '',
    component: UserContactComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.userContact.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: UserContactDetailComponent,
    resolve: {
      userContact: UserContactResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.userContact.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UserContactUpdateComponent,
    resolve: {
      userContact: UserContactResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.userContact.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UserContactUpdateComponent,
    resolve: {
      userContact: UserContactResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.userContact.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const userContactPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: UserContactDeletePopupComponent,
    resolve: {
      userContact: UserContactResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.userContact.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
