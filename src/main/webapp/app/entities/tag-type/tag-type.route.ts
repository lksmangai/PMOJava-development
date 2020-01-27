import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TagType } from 'app/shared/model/tag-type.model';
import { TagTypeService } from './tag-type.service';
import { TagTypeComponent } from './tag-type.component';
import { TagTypeDetailComponent } from './tag-type-detail.component';
import { TagTypeUpdateComponent } from './tag-type-update.component';
import { TagTypeDeletePopupComponent } from './tag-type-delete-dialog.component';
import { ITagType } from 'app/shared/model/tag-type.model';

@Injectable({ providedIn: 'root' })
export class TagTypeResolve implements Resolve<ITagType> {
  constructor(private service: TagTypeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITagType> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<TagType>) => response.ok),
        map((tagType: HttpResponse<TagType>) => tagType.body)
      );
    }
    return of(new TagType());
  }
}

export const tagTypeRoute: Routes = [
  {
    path: '',
    component: TagTypeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.tagType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TagTypeDetailComponent,
    resolve: {
      tagType: TagTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.tagType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TagTypeUpdateComponent,
    resolve: {
      tagType: TagTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.tagType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TagTypeUpdateComponent,
    resolve: {
      tagType: TagTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.tagType.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const tagTypePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TagTypeDeletePopupComponent,
    resolve: {
      tagType: TagTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.tagType.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
