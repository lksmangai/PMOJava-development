import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProjectInitiativeId } from 'app/shared/model/project-initiative-id.model';
import { AccountService } from 'app/core';
import { ProjectInitiativeIdService } from './project-initiative-id.service';

@Component({
  selector: 'jhi-project-initiative-id',
  templateUrl: './project-initiative-id.component.html'
})
export class ProjectInitiativeIdComponent implements OnInit, OnDestroy {
  projectInitiativeIds: IProjectInitiativeId[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected projectInitiativeIdService: ProjectInitiativeIdService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.projectInitiativeIdService
      .query()
      .pipe(
        filter((res: HttpResponse<IProjectInitiativeId[]>) => res.ok),
        map((res: HttpResponse<IProjectInitiativeId[]>) => res.body)
      )
      .subscribe(
        (res: IProjectInitiativeId[]) => {
          this.projectInitiativeIds = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProjectInitiativeIds();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProjectInitiativeId) {
    return item.id;
  }

  registerChangeInProjectInitiativeIds() {
    this.eventSubscriber = this.eventManager.subscribe('projectInitiativeIdListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
