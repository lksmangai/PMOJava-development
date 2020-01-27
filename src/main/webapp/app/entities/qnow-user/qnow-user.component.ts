import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IQnowUser } from 'app/shared/model/qnow-user.model';
import { AccountService } from 'app/core';
import { QnowUserService } from './qnow-user.service';

@Component({
  selector: 'jhi-qnow-user',
  templateUrl: './qnow-user.component.html'
})
export class QnowUserComponent implements OnInit, OnDestroy {
  qnowUsers: IQnowUser[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected qnowUserService: QnowUserService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.qnowUserService
      .query()
      .pipe(
        filter((res: HttpResponse<IQnowUser[]>) => res.ok),
        map((res: HttpResponse<IQnowUser[]>) => res.body)
      )
      .subscribe(
        (res: IQnowUser[]) => {
          this.qnowUsers = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInQnowUsers();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IQnowUser) {
    return item.id;
  }

  registerChangeInQnowUsers() {
    this.eventSubscriber = this.eventManager.subscribe('qnowUserListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
