import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IImTimesheet } from 'app/shared/model/im-timesheet.model';
import { AccountService } from 'app/core';
import { ImTimesheetService } from './im-timesheet.service';

@Component({
  selector: 'jhi-im-timesheet',
  templateUrl: './im-timesheet.component.html'
})
export class ImTimesheetComponent implements OnInit, OnDestroy {
  imTimesheets: IImTimesheet[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected imTimesheetService: ImTimesheetService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.imTimesheetService
      .query()
      .pipe(
        filter((res: HttpResponse<IImTimesheet[]>) => res.ok),
        map((res: HttpResponse<IImTimesheet[]>) => res.body)
      )
      .subscribe(
        (res: IImTimesheet[]) => {
          this.imTimesheets = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInImTimesheets();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IImTimesheet) {
    return item.id;
  }

  registerChangeInImTimesheets() {
    this.eventSubscriber = this.eventManager.subscribe('imTimesheetListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
