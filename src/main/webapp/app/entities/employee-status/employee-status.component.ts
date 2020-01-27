import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEmployeeStatus } from 'app/shared/model/employee-status.model';
import { AccountService } from 'app/core';
import { EmployeeStatusService } from './employee-status.service';

@Component({
  selector: 'jhi-employee-status',
  templateUrl: './employee-status.component.html'
})
export class EmployeeStatusComponent implements OnInit, OnDestroy {
  employeeStatuses: IEmployeeStatus[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected employeeStatusService: EmployeeStatusService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.employeeStatusService
      .query()
      .pipe(
        filter((res: HttpResponse<IEmployeeStatus[]>) => res.ok),
        map((res: HttpResponse<IEmployeeStatus[]>) => res.body)
      )
      .subscribe(
        (res: IEmployeeStatus[]) => {
          this.employeeStatuses = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInEmployeeStatuses();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IEmployeeStatus) {
    return item.id;
  }

  registerChangeInEmployeeStatuses() {
    this.eventSubscriber = this.eventManager.subscribe('employeeStatusListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
