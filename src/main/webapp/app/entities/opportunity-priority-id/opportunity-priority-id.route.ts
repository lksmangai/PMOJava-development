import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { OpportunityPriorityId } from 'app/shared/model/opportunity-priority-id.model';
import { OpportunityPriorityIdService } from './opportunity-priority-id.service';
import { OpportunityPriorityIdComponent } from './opportunity-priority-id.component';
import { OpportunityPriorityIdDetailComponent } from './opportunity-priority-id-detail.component';
import { OpportunityPriorityIdUpdateComponent } from './opportunity-priority-id-update.component';
import { OpportunityPriorityIdDeletePopupComponent } from './opportunity-priority-id-delete-dialog.component';
import { IOpportunityPriorityId } from 'app/shared/model/opportunity-priority-id.model';

@Injectable({ providedIn: 'root' })
export class OpportunityPriorityIdResolve implements Resolve<IOpportunityPriorityId> {
  constructor(private service: OpportunityPriorityIdService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IOpportunityPriorityId> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<OpportunityPriorityId>) => response.ok),
        map((opportunityPriorityId: HttpResponse<OpportunityPriorityId>) => opportunityPriorityId.body)
      );
    }
    return of(new OpportunityPriorityId());
  }
}

export const opportunityPriorityIdRoute: Routes = [
  {
    path: '',
    component: OpportunityPriorityIdComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.opportunityPriorityId.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: OpportunityPriorityIdDetailComponent,
    resolve: {
      opportunityPriorityId: OpportunityPriorityIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.opportunityPriorityId.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: OpportunityPriorityIdUpdateComponent,
    resolve: {
      opportunityPriorityId: OpportunityPriorityIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.opportunityPriorityId.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: OpportunityPriorityIdUpdateComponent,
    resolve: {
      opportunityPriorityId: OpportunityPriorityIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.opportunityPriorityId.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const opportunityPriorityIdPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: OpportunityPriorityIdDeletePopupComponent,
    resolve: {
      opportunityPriorityId: OpportunityPriorityIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.opportunityPriorityId.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
