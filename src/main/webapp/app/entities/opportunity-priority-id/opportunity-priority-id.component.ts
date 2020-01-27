import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IOpportunityPriorityId } from 'app/shared/model/opportunity-priority-id.model';
import { AccountService } from 'app/core';
import { OpportunityPriorityIdService } from './opportunity-priority-id.service';

@Component({
  selector: 'jhi-opportunity-priority-id',
  templateUrl: './opportunity-priority-id.component.html'
})
export class OpportunityPriorityIdComponent implements OnInit, OnDestroy {
  opportunityPriorityIds: IOpportunityPriorityId[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected opportunityPriorityIdService: OpportunityPriorityIdService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.opportunityPriorityIdService
      .query()
      .pipe(
        filter((res: HttpResponse<IOpportunityPriorityId[]>) => res.ok),
        map((res: HttpResponse<IOpportunityPriorityId[]>) => res.body)
      )
      .subscribe(
        (res: IOpportunityPriorityId[]) => {
          this.opportunityPriorityIds = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInOpportunityPriorityIds();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IOpportunityPriorityId) {
    return item.id;
  }

  registerChangeInOpportunityPriorityIds() {
    this.eventSubscriber = this.eventManager.subscribe('opportunityPriorityIdListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
