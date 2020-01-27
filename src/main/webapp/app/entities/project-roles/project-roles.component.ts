import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProjectRoles } from 'app/shared/model/project-roles.model';
import { AccountService } from 'app/core';
import { ProjectRolesService } from './project-roles.service';

@Component({
  selector: 'jhi-project-roles',
  templateUrl: './project-roles.component.html'
})
export class ProjectRolesComponent implements OnInit, OnDestroy {
  projectRoles: IProjectRoles[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected projectRolesService: ProjectRolesService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.projectRolesService
      .query()
      .pipe(
        filter((res: HttpResponse<IProjectRoles[]>) => res.ok),
        map((res: HttpResponse<IProjectRoles[]>) => res.body)
      )
      .subscribe(
        (res: IProjectRoles[]) => {
          this.projectRoles = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProjectRoles();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProjectRoles) {
    return item.id;
  }

  registerChangeInProjectRoles() {
    this.eventSubscriber = this.eventManager.subscribe('projectRolesListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
