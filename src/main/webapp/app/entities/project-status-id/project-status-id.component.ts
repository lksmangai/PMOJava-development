import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProjectStatusId } from 'app/shared/model/project-status-id.model';
import { AccountService } from 'app/core';
import { ProjectStatusIdService } from './project-status-id.service';

@Component({
  selector: 'jhi-project-status-id',
  templateUrl: './project-status-id.component.html'
})
export class ProjectStatusIdComponent implements OnInit, OnDestroy {
  projectStatusIds: IProjectStatusId[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected projectStatusIdService: ProjectStatusIdService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.projectStatusIdService
      .query()
      .pipe(
        filter((res: HttpResponse<IProjectStatusId[]>) => res.ok),
        map((res: HttpResponse<IProjectStatusId[]>) => res.body)
      )
      .subscribe(
        (res: IProjectStatusId[]) => {
          this.projectStatusIds = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProjectStatusIds();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProjectStatusId) {
    return item.id;
  }

  registerChangeInProjectStatusIds() {
    this.eventSubscriber = this.eventManager.subscribe('projectStatusIdListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
