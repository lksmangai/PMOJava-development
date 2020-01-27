import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProjectClass } from 'app/shared/model/project-class.model';
import { AccountService } from 'app/core';
import { ProjectClassService } from './project-class.service';

@Component({
  selector: 'jhi-project-class',
  templateUrl: './project-class.component.html'
})
export class ProjectClassComponent implements OnInit, OnDestroy {
  projectClasses: IProjectClass[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected projectClassService: ProjectClassService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.projectClassService
      .query()
      .pipe(
        filter((res: HttpResponse<IProjectClass[]>) => res.ok),
        map((res: HttpResponse<IProjectClass[]>) => res.body)
      )
      .subscribe(
        (res: IProjectClass[]) => {
          this.projectClasses = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProjectClasses();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProjectClass) {
    return item.id;
  }

  registerChangeInProjectClasses() {
    this.eventSubscriber = this.eventManager.subscribe('projectClassListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
