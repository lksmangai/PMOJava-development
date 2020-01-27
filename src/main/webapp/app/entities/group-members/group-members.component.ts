import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IGroupMembers } from 'app/shared/model/group-members.model';
import { AccountService } from 'app/core';
import { GroupMembersService } from './group-members.service';

@Component({
  selector: 'jhi-group-members',
  templateUrl: './group-members.component.html'
})
export class GroupMembersComponent implements OnInit, OnDestroy {
  groupMembers: IGroupMembers[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected groupMembersService: GroupMembersService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.groupMembersService
      .query()
      .pipe(
        filter((res: HttpResponse<IGroupMembers[]>) => res.ok),
        map((res: HttpResponse<IGroupMembers[]>) => res.body)
      )
      .subscribe(
        (res: IGroupMembers[]) => {
          this.groupMembers = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInGroupMembers();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IGroupMembers) {
    return item.id;
  }

  registerChangeInGroupMembers() {
    this.eventSubscriber = this.eventManager.subscribe('groupMembersListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
