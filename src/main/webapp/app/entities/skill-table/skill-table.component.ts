import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISkillTable } from 'app/shared/model/skill-table.model';
import { AccountService } from 'app/core';
import { SkillTableService } from './skill-table.service';

@Component({
  selector: 'jhi-skill-table',
  templateUrl: './skill-table.component.html'
})
export class SkillTableComponent implements OnInit, OnDestroy {
  skillTables: ISkillTable[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected skillTableService: SkillTableService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.skillTableService
      .query()
      .pipe(
        filter((res: HttpResponse<ISkillTable[]>) => res.ok),
        map((res: HttpResponse<ISkillTable[]>) => res.body)
      )
      .subscribe(
        (res: ISkillTable[]) => {
          this.skillTables = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInSkillTables();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISkillTable) {
    return item.id;
  }

  registerChangeInSkillTables() {
    this.eventSubscriber = this.eventManager.subscribe('skillTableListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
