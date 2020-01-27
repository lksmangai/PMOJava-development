import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProjectBucketId } from 'app/shared/model/project-bucket-id.model';
import { AccountService } from 'app/core';
import { ProjectBucketIdService } from './project-bucket-id.service';

@Component({
  selector: 'jhi-project-bucket-id',
  templateUrl: './project-bucket-id.component.html'
})
export class ProjectBucketIdComponent implements OnInit, OnDestroy {
  projectBucketIds: IProjectBucketId[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected projectBucketIdService: ProjectBucketIdService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.projectBucketIdService
      .query()
      .pipe(
        filter((res: HttpResponse<IProjectBucketId[]>) => res.ok),
        map((res: HttpResponse<IProjectBucketId[]>) => res.body)
      )
      .subscribe(
        (res: IProjectBucketId[]) => {
          this.projectBucketIds = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProjectBucketIds();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProjectBucketId) {
    return item.id;
  }

  registerChangeInProjectBucketIds() {
    this.eventSubscriber = this.eventManager.subscribe('projectBucketIdListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
