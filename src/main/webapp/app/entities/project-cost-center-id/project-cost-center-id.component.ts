import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProjectCostCenterId } from 'app/shared/model/project-cost-center-id.model';
import { AccountService } from 'app/core';
import { ProjectCostCenterIdService } from './project-cost-center-id.service';

@Component({
  selector: 'jhi-project-cost-center-id',
  templateUrl: './project-cost-center-id.component.html'
})
export class ProjectCostCenterIdComponent implements OnInit, OnDestroy {
  projectCostCenterIds: IProjectCostCenterId[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected projectCostCenterIdService: ProjectCostCenterIdService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.projectCostCenterIdService
      .query()
      .pipe(
        filter((res: HttpResponse<IProjectCostCenterId[]>) => res.ok),
        map((res: HttpResponse<IProjectCostCenterId[]>) => res.body)
      )
      .subscribe(
        (res: IProjectCostCenterId[]) => {
          this.projectCostCenterIds = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProjectCostCenterIds();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProjectCostCenterId) {
    return item.id;
  }

  registerChangeInProjectCostCenterIds() {
    this.eventSubscriber = this.eventManager.subscribe('projectCostCenterIdListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
