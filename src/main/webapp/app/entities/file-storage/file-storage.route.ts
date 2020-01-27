import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { FileStorage } from 'app/shared/model/file-storage.model';
import { FileStorageService } from './file-storage.service';
import { FileStorageComponent } from './file-storage.component';
import { FileStorageDetailComponent } from './file-storage-detail.component';
import { FileStorageUpdateComponent } from './file-storage-update.component';
import { FileStorageDeletePopupComponent } from './file-storage-delete-dialog.component';
import { IFileStorage } from 'app/shared/model/file-storage.model';

@Injectable({ providedIn: 'root' })
export class FileStorageResolve implements Resolve<IFileStorage> {
  constructor(private service: FileStorageService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFileStorage> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<FileStorage>) => response.ok),
        map((fileStorage: HttpResponse<FileStorage>) => fileStorage.body)
      );
    }
    return of(new FileStorage());
  }
}

export const fileStorageRoute: Routes = [
  {
    path: '',
    component: FileStorageComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.fileStorage.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FileStorageDetailComponent,
    resolve: {
      fileStorage: FileStorageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.fileStorage.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FileStorageUpdateComponent,
    resolve: {
      fileStorage: FileStorageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.fileStorage.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FileStorageUpdateComponent,
    resolve: {
      fileStorage: FileStorageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.fileStorage.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const fileStoragePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: FileStorageDeletePopupComponent,
    resolve: {
      fileStorage: FileStorageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.fileStorage.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
