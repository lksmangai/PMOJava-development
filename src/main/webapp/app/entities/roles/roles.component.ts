import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRoles } from 'app/shared/model/roles.model';
import { AccountService } from 'app/core';
import { RolesService } from './roles.service';

@Component({
  selector: 'jhi-roles',
  templateUrl: './roles.component.html'
})
export class RolesComponent implements OnInit, OnDestroy {
  roles: IRoles[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected rolesService: RolesService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.rolesService
      .query()
      .pipe(
        filter((res: HttpResponse<IRoles[]>) => res.ok),
        map((res: HttpResponse<IRoles[]>) => res.body)
      )
      .subscribe(
        (res: IRoles[]) => {
          this.roles = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInRoles();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IRoles) {
    return item.id;
  }

  registerChangeInRoles() {
    this.eventSubscriber = this.eventManager.subscribe('rolesListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
