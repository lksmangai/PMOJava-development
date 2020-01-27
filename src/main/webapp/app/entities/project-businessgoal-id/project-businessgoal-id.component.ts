import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProjectBusinessgoalId } from 'app/shared/model/project-businessgoal-id.model';
import { AccountService } from 'app/core';
import { ProjectBusinessgoalIdService } from './project-businessgoal-id.service';

@Component({
  selector: 'jhi-project-businessgoal-id',
  templateUrl: './project-businessgoal-id.component.html'
})
export class ProjectBusinessgoalIdComponent implements OnInit, OnDestroy {
  projectBusinessgoalIds: IProjectBusinessgoalId[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected projectBusinessgoalIdService: ProjectBusinessgoalIdService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.projectBusinessgoalIdService
      .query()
      .pipe(
        filter((res: HttpResponse<IProjectBusinessgoalId[]>) => res.ok),
        map((res: HttpResponse<IProjectBusinessgoalId[]>) => res.body)
      )
      .subscribe(
        (res: IProjectBusinessgoalId[]) => {
          this.projectBusinessgoalIds = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProjectBusinessgoalIds();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProjectBusinessgoalId) {
    return item.id;
  }

  registerChangeInProjectBusinessgoalIds() {
    this.eventSubscriber = this.eventManager.subscribe('projectBusinessgoalIdListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
