import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  ProjectBusinessgoalIdComponent,
  ProjectBusinessgoalIdDetailComponent,
  ProjectBusinessgoalIdUpdateComponent,
  ProjectBusinessgoalIdDeletePopupComponent,
  ProjectBusinessgoalIdDeleteDialogComponent,
  projectBusinessgoalIdRoute,
  projectBusinessgoalIdPopupRoute
} from './';

const ENTITY_STATES = [...projectBusinessgoalIdRoute, ...projectBusinessgoalIdPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProjectBusinessgoalIdComponent,
    ProjectBusinessgoalIdDetailComponent,
    ProjectBusinessgoalIdUpdateComponent,
    ProjectBusinessgoalIdDeleteDialogComponent,
    ProjectBusinessgoalIdDeletePopupComponent
  ],
  entryComponents: [
    ProjectBusinessgoalIdComponent,
    ProjectBusinessgoalIdUpdateComponent,
    ProjectBusinessgoalIdDeleteDialogComponent,
    ProjectBusinessgoalIdDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppProjectBusinessgoalIdModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
