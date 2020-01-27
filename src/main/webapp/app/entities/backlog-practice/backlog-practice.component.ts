import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBacklogPractice } from 'app/shared/model/backlog-practice.model';
import { AccountService } from 'app/core';
import { BacklogPracticeService } from './backlog-practice.service';

@Component({
  selector: 'jhi-backlog-practice',
  templateUrl: './backlog-practice.component.html'
})
export class BacklogPracticeComponent implements OnInit, OnDestroy {
  backlogPractices: IBacklogPractice[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected backlogPracticeService: BacklogPracticeService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.backlogPracticeService
      .query()
      .pipe(
        filter((res: HttpResponse<IBacklogPractice[]>) => res.ok),
        map((res: HttpResponse<IBacklogPractice[]>) => res.body)
      )
      .subscribe(
        (res: IBacklogPractice[]) => {
          this.backlogPractices = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInBacklogPractices();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IBacklogPractice) {
    return item.id;
  }

  registerChangeInBacklogPractices() {
    this.eventSubscriber = this.eventManager.subscribe('backlogPracticeListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
