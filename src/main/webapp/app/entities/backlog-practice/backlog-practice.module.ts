import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  BacklogPracticeComponent,
  BacklogPracticeDetailComponent,
  BacklogPracticeUpdateComponent,
  BacklogPracticeDeletePopupComponent,
  BacklogPracticeDeleteDialogComponent,
  backlogPracticeRoute,
  backlogPracticePopupRoute
} from './';

const ENTITY_STATES = [...backlogPracticeRoute, ...backlogPracticePopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    BacklogPracticeComponent,
    BacklogPracticeDetailComponent,
    BacklogPracticeUpdateComponent,
    BacklogPracticeDeleteDialogComponent,
    BacklogPracticeDeletePopupComponent
  ],
  entryComponents: [
    BacklogPracticeComponent,
    BacklogPracticeUpdateComponent,
    BacklogPracticeDeleteDialogComponent,
    BacklogPracticeDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppBacklogPracticeModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
