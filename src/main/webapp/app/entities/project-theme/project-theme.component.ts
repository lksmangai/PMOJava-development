import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProjectTheme } from 'app/shared/model/project-theme.model';
import { AccountService } from 'app/core';
import { ProjectThemeService } from './project-theme.service';

@Component({
  selector: 'jhi-project-theme',
  templateUrl: './project-theme.component.html'
})
export class ProjectThemeComponent implements OnInit, OnDestroy {
  projectThemes: IProjectTheme[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected projectThemeService: ProjectThemeService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.projectThemeService
      .query()
      .pipe(
        filter((res: HttpResponse<IProjectTheme[]>) => res.ok),
        map((res: HttpResponse<IProjectTheme[]>) => res.body)
      )
      .subscribe(
        (res: IProjectTheme[]) => {
          this.projectThemes = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProjectThemes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProjectTheme) {
    return item.id;
  }

  registerChangeInProjectThemes() {
    this.eventSubscriber = this.eventManager.subscribe('projectThemeListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
