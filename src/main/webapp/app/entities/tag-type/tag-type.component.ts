import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITagType } from 'app/shared/model/tag-type.model';
import { AccountService } from 'app/core';
import { TagTypeService } from './tag-type.service';

@Component({
  selector: 'jhi-tag-type',
  templateUrl: './tag-type.component.html'
})
export class TagTypeComponent implements OnInit, OnDestroy {
  tagTypes: ITagType[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected tagTypeService: TagTypeService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.tagTypeService
      .query()
      .pipe(
        filter((res: HttpResponse<ITagType[]>) => res.ok),
        map((res: HttpResponse<ITagType[]>) => res.body)
      )
      .subscribe(
        (res: ITagType[]) => {
          this.tagTypes = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInTagTypes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITagType) {
    return item.id;
  }

  registerChangeInTagTypes() {
    this.eventSubscriber = this.eventManager.subscribe('tagTypeListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
