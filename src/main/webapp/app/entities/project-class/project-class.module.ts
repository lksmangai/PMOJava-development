import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  ProjectClassComponent,
  ProjectClassDetailComponent,
  ProjectClassUpdateComponent,
  ProjectClassDeletePopupComponent,
  ProjectClassDeleteDialogComponent,
  projectClassRoute,
  projectClassPopupRoute
} from './';

const ENTITY_STATES = [...projectClassRoute, ...projectClassPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProjectClassComponent,
    ProjectClassDetailComponent,
    ProjectClassUpdateComponent,
    ProjectClassDeleteDialogComponent,
    ProjectClassDeletePopupComponent
  ],
  entryComponents: [
    ProjectClassComponent,
    ProjectClassUpdateComponent,
    ProjectClassDeleteDialogComponent,
    ProjectClassDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppProjectClassModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
