import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISkillExpertise } from 'app/shared/model/skill-expertise.model';
import { AccountService } from 'app/core';
import { SkillExpertiseService } from './skill-expertise.service';

@Component({
  selector: 'jhi-skill-expertise',
  templateUrl: './skill-expertise.component.html'
})
export class SkillExpertiseComponent implements OnInit, OnDestroy {
  skillExpertises: ISkillExpertise[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected skillExpertiseService: SkillExpertiseService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.skillExpertiseService
      .query()
      .pipe(
        filter((res: HttpResponse<ISkillExpertise[]>) => res.ok),
        map((res: HttpResponse<ISkillExpertise[]>) => res.body)
      )
      .subscribe(
        (res: ISkillExpertise[]) => {
          this.skillExpertises = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInSkillExpertises();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISkillExpertise) {
    return item.id;
  }

  registerChangeInSkillExpertises() {
    this.eventSubscriber = this.eventManager.subscribe('skillExpertiseListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
