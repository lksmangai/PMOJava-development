import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProjectTag } from 'app/shared/model/project-tag.model';
import { AccountService } from 'app/core';
import { ProjectTagService } from './project-tag.service';

@Component({
  selector: 'jhi-project-tag',
  templateUrl: './project-tag.component.html'
})
export class ProjectTagComponent implements OnInit, OnDestroy {
  projectTags: IProjectTag[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected projectTagService: ProjectTagService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.projectTagService
      .query()
      .pipe(
        filter((res: HttpResponse<IProjectTag[]>) => res.ok),
        map((res: HttpResponse<IProjectTag[]>) => res.body)
      )
      .subscribe(
        (res: IProjectTag[]) => {
          this.projectTags = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProjectTags();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProjectTag) {
    return item.id;
  }

  registerChangeInProjectTags() {
    this.eventSubscriber = this.eventManager.subscribe('projectTagListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
