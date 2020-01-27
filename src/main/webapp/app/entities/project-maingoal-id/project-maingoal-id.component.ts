import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProjectMaingoalId } from 'app/shared/model/project-maingoal-id.model';
import { AccountService } from 'app/core';
import { ProjectMaingoalIdService } from './project-maingoal-id.service';

@Component({
  selector: 'jhi-project-maingoal-id',
  templateUrl: './project-maingoal-id.component.html'
})
export class ProjectMaingoalIdComponent implements OnInit, OnDestroy {
  projectMaingoalIds: IProjectMaingoalId[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected projectMaingoalIdService: ProjectMaingoalIdService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.projectMaingoalIdService
      .query()
      .pipe(
        filter((res: HttpResponse<IProjectMaingoalId[]>) => res.ok),
        map((res: HttpResponse<IProjectMaingoalId[]>) => res.body)
      )
      .subscribe(
        (res: IProjectMaingoalId[]) => {
          this.projectMaingoalIds = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProjectMaingoalIds();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProjectMaingoalId) {
    return item.id;
  }

  registerChangeInProjectMaingoalIds() {
    this.eventSubscriber = this.eventManager.subscribe('projectMaingoalIdListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
