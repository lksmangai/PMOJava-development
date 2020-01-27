import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  ProjectTypeIdComponent,
  ProjectTypeIdDetailComponent,
  ProjectTypeIdUpdateComponent,
  ProjectTypeIdDeletePopupComponent,
  ProjectTypeIdDeleteDialogComponent,
  projectTypeIdRoute,
  projectTypeIdPopupRoute
} from './';

const ENTITY_STATES = [...projectTypeIdRoute, ...projectTypeIdPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProjectTypeIdComponent,
    ProjectTypeIdDetailComponent,
    ProjectTypeIdUpdateComponent,
    ProjectTypeIdDeleteDialogComponent,
    ProjectTypeIdDeletePopupComponent
  ],
  entryComponents: [
    ProjectTypeIdComponent,
    ProjectTypeIdUpdateComponent,
    ProjectTypeIdDeleteDialogComponent,
    ProjectTypeIdDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppProjectTypeIdModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
