import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IImProjects } from 'app/shared/model/im-projects.model';
import { AccountService } from 'app/core';
import { ImProjectsService } from './im-projects.service';

@Component({
  selector: 'jhi-im-projects',
  templateUrl: './im-projects.component.html'
})
export class ImProjectsComponent implements OnInit, OnDestroy {
  imProjects: IImProjects[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected imProjectsService: ImProjectsService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.imProjectsService
      .query()
      .pipe(
        filter((res: HttpResponse<IImProjects[]>) => res.ok),
        map((res: HttpResponse<IImProjects[]>) => res.body)
      )
      .subscribe(
        (res: IImProjects[]) => {
          this.imProjects = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInImProjects();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IImProjects) {
    return item.id;
  }

  registerChangeInImProjects() {
    this.eventSubscriber = this.eventManager.subscribe('imProjectsListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
