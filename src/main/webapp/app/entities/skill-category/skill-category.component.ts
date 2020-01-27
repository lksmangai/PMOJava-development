import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISkillCategory } from 'app/shared/model/skill-category.model';
import { AccountService } from 'app/core';
import { SkillCategoryService } from './skill-category.service';

@Component({
  selector: 'jhi-skill-category',
  templateUrl: './skill-category.component.html'
})
export class SkillCategoryComponent implements OnInit, OnDestroy {
  skillCategories: ISkillCategory[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected skillCategoryService: SkillCategoryService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.skillCategoryService
      .query()
      .pipe(
        filter((res: HttpResponse<ISkillCategory[]>) => res.ok),
        map((res: HttpResponse<ISkillCategory[]>) => res.body)
      )
      .subscribe(
        (res: ISkillCategory[]) => {
          this.skillCategories = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInSkillCategories();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISkillCategory) {
    return item.id;
  }

  registerChangeInSkillCategories() {
    this.eventSubscriber = this.eventManager.subscribe('skillCategoryListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
