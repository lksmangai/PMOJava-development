import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IImEmployee } from 'app/shared/model/im-employee.model';
import { AccountService } from 'app/core';
import { ImEmployeeService } from './im-employee.service';

@Component({
  selector: 'jhi-im-employee',
  templateUrl: './im-employee.component.html'
})
export class ImEmployeeComponent implements OnInit, OnDestroy {
  imEmployees: IImEmployee[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected imEmployeeService: ImEmployeeService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.imEmployeeService
      .query()
      .pipe(
        filter((res: HttpResponse<IImEmployee[]>) => res.ok),
        map((res: HttpResponse<IImEmployee[]>) => res.body)
      )
      .subscribe(
        (res: IImEmployee[]) => {
          this.imEmployees = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInImEmployees();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IImEmployee) {
    return item.id;
  }

  registerChangeInImEmployees() {
    this.eventSubscriber = this.eventManager.subscribe('imEmployeeListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
