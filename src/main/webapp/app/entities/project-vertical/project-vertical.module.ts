import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  ProjectVerticalComponent,
  ProjectVerticalDetailComponent,
  ProjectVerticalUpdateComponent,
  ProjectVerticalDeletePopupComponent,
  ProjectVerticalDeleteDialogComponent,
  projectVerticalRoute,
  projectVerticalPopupRoute
} from './';

const ENTITY_STATES = [...projectVerticalRoute, ...projectVerticalPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProjectVerticalComponent,
    ProjectVerticalDetailComponent,
    ProjectVerticalUpdateComponent,
    ProjectVerticalDeleteDialogComponent,
    ProjectVerticalDeletePopupComponent
  ],
  entryComponents: [
    ProjectVerticalComponent,
    ProjectVerticalUpdateComponent,
    ProjectVerticalDeleteDialogComponent,
    ProjectVerticalDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppProjectVerticalModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
