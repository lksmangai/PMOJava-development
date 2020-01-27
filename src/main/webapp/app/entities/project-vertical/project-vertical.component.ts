import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProjectVertical } from 'app/shared/model/project-vertical.model';
import { AccountService } from 'app/core';
import { ProjectVerticalService } from './project-vertical.service';

@Component({
  selector: 'jhi-project-vertical',
  templateUrl: './project-vertical.component.html'
})
export class ProjectVerticalComponent implements OnInit, OnDestroy {
  projectVerticals: IProjectVertical[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected projectVerticalService: ProjectVerticalService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.projectVerticalService
      .query()
      .pipe(
        filter((res: HttpResponse<IProjectVertical[]>) => res.ok),
        map((res: HttpResponse<IProjectVertical[]>) => res.body)
      )
      .subscribe(
        (res: IProjectVertical[]) => {
          this.projectVerticals = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProjectVerticals();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProjectVertical) {
    return item.id;
  }

  registerChangeInProjectVerticals() {
    this.eventSubscriber = this.eventManager.subscribe('projectVerticalListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
