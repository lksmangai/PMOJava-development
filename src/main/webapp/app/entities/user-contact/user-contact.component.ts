import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IUserContact } from 'app/shared/model/user-contact.model';
import { AccountService } from 'app/core';
import { UserContactService } from './user-contact.service';

@Component({
  selector: 'jhi-user-contact',
  templateUrl: './user-contact.component.html'
})
export class UserContactComponent implements OnInit, OnDestroy {
  userContacts: IUserContact[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected userContactService: UserContactService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.userContactService
      .query()
      .pipe(
        filter((res: HttpResponse<IUserContact[]>) => res.ok),
        map((res: HttpResponse<IUserContact[]>) => res.body)
      )
      .subscribe(
        (res: IUserContact[]) => {
          this.userContacts = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInUserContacts();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IUserContact) {
    return item.id;
  }

  registerChangeInUserContacts() {
    this.eventSubscriber = this.eventManager.subscribe('userContactListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
