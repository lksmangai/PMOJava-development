import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProjectSubgoalId } from 'app/shared/model/project-subgoal-id.model';
import { AccountService } from 'app/core';
import { ProjectSubgoalIdService } from './project-subgoal-id.service';

@Component({
  selector: 'jhi-project-subgoal-id',
  templateUrl: './project-subgoal-id.component.html'
})
export class ProjectSubgoalIdComponent implements OnInit, OnDestroy {
  projectSubgoalIds: IProjectSubgoalId[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected projectSubgoalIdService: ProjectSubgoalIdService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.projectSubgoalIdService
      .query()
      .pipe(
        filter((res: HttpResponse<IProjectSubgoalId[]>) => res.ok),
        map((res: HttpResponse<IProjectSubgoalId[]>) => res.body)
      )
      .subscribe(
        (res: IProjectSubgoalId[]) => {
          this.projectSubgoalIds = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProjectSubgoalIds();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProjectSubgoalId) {
    return item.id;
  }

  registerChangeInProjectSubgoalIds() {
    this.eventSubscriber = this.eventManager.subscribe('projectSubgoalIdListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
