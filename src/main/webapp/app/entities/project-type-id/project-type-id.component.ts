import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProjectTypeId } from 'app/shared/model/project-type-id.model';
import { AccountService } from 'app/core';
import { ProjectTypeIdService } from './project-type-id.service';

@Component({
  selector: 'jhi-project-type-id',
  templateUrl: './project-type-id.component.html'
})
export class ProjectTypeIdComponent implements OnInit, OnDestroy {
  projectTypeIds: IProjectTypeId[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected projectTypeIdService: ProjectTypeIdService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.projectTypeIdService
      .query()
      .pipe(
        filter((res: HttpResponse<IProjectTypeId[]>) => res.ok),
        map((res: HttpResponse<IProjectTypeId[]>) => res.body)
      )
      .subscribe(
        (res: IProjectTypeId[]) => {
          this.projectTypeIds = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProjectTypeIds();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProjectTypeId) {
    return item.id;
  }

  registerChangeInProjectTypeIds() {
    this.eventSubscriber = this.eventManager.subscribe('projectTypeIdListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
