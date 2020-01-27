import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISkills } from 'app/shared/model/skills.model';
import { AccountService } from 'app/core';
import { SkillsService } from './skills.service';

@Component({
  selector: 'jhi-skills',
  templateUrl: './skills.component.html'
})
export class SkillsComponent implements OnInit, OnDestroy {
  skills: ISkills[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected skillsService: SkillsService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.skillsService
      .query()
      .pipe(
        filter((res: HttpResponse<ISkills[]>) => res.ok),
        map((res: HttpResponse<ISkills[]>) => res.body)
      )
      .subscribe(
        (res: ISkills[]) => {
          this.skills = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInSkills();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISkills) {
    return item.id;
  }

  registerChangeInSkills() {
    this.eventSubscriber = this.eventManager.subscribe('skillsListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
