import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProjectBoardId } from 'app/shared/model/project-board-id.model';
import { AccountService } from 'app/core';
import { ProjectBoardIdService } from './project-board-id.service';

@Component({
  selector: 'jhi-project-board-id',
  templateUrl: './project-board-id.component.html'
})
export class ProjectBoardIdComponent implements OnInit, OnDestroy {
  projectBoardIds: IProjectBoardId[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected projectBoardIdService: ProjectBoardIdService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.projectBoardIdService
      .query()
      .pipe(
        filter((res: HttpResponse<IProjectBoardId[]>) => res.ok),
        map((res: HttpResponse<IProjectBoardId[]>) => res.body)
      )
      .subscribe(
        (res: IProjectBoardId[]) => {
          this.projectBoardIds = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProjectBoardIds();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProjectBoardId) {
    return item.id;
  }

  registerChangeInProjectBoardIds() {
    this.eventSubscriber = this.eventManager.subscribe('projectBoardIdListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
