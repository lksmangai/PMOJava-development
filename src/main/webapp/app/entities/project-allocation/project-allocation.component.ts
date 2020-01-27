import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProjectAllocation } from 'app/shared/model/project-allocation.model';
import { AccountService } from 'app/core';
import { ProjectAllocationService } from './project-allocation.service';

@Component({
  selector: 'jhi-project-allocation',
  templateUrl: './project-allocation.component.html'
})
export class ProjectAllocationComponent implements OnInit, OnDestroy {
  projectAllocations: IProjectAllocation[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected projectAllocationService: ProjectAllocationService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.projectAllocationService
      .query()
      .pipe(
        filter((res: HttpResponse<IProjectAllocation[]>) => res.ok),
        map((res: HttpResponse<IProjectAllocation[]>) => res.body)
      )
      .subscribe(
        (res: IProjectAllocation[]) => {
          this.projectAllocations = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProjectAllocations();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProjectAllocation) {
    return item.id;
  }

  registerChangeInProjectAllocations() {
    this.eventSubscriber = this.eventManager.subscribe('projectAllocationListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
