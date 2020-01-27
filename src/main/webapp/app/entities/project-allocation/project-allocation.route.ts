import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProjectAllocation } from 'app/shared/model/project-allocation.model';
import { ProjectAllocationService } from './project-allocation.service';
import { ProjectAllocationComponent } from './project-allocation.component';
import { ProjectAllocationDetailComponent } from './project-allocation-detail.component';
import { ProjectAllocationUpdateComponent } from './project-allocation-update.component';
import { ProjectAllocationDeletePopupComponent } from './project-allocation-delete-dialog.component';
import { IProjectAllocation } from 'app/shared/model/project-allocation.model';

@Injectable({ providedIn: 'root' })
export class ProjectAllocationResolve implements Resolve<IProjectAllocation> {
  constructor(private service: ProjectAllocationService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProjectAllocation> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ProjectAllocation>) => response.ok),
        map((projectAllocation: HttpResponse<ProjectAllocation>) => projectAllocation.body)
      );
    }
    return of(new ProjectAllocation());
  }
}

export const projectAllocationRoute: Routes = [
  {
    path: '',
    component: ProjectAllocationComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectAllocation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProjectAllocationDetailComponent,
    resolve: {
      projectAllocation: ProjectAllocationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectAllocation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProjectAllocationUpdateComponent,
    resolve: {
      projectAllocation: ProjectAllocationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectAllocation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProjectAllocationUpdateComponent,
    resolve: {
      projectAllocation: ProjectAllocationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectAllocation.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const projectAllocationPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProjectAllocationDeletePopupComponent,
    resolve: {
      projectAllocation: ProjectAllocationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.projectAllocation.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
