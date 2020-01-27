import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  ProjectStatusIdComponent,
  ProjectStatusIdDetailComponent,
  ProjectStatusIdUpdateComponent,
  ProjectStatusIdDeletePopupComponent,
  ProjectStatusIdDeleteDialogComponent,
  projectStatusIdRoute,
  projectStatusIdPopupRoute
} from './';

const ENTITY_STATES = [...projectStatusIdRoute, ...projectStatusIdPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProjectStatusIdComponent,
    ProjectStatusIdDetailComponent,
    ProjectStatusIdUpdateComponent,
    ProjectStatusIdDeleteDialogComponent,
    ProjectStatusIdDeletePopupComponent
  ],
  entryComponents: [
    ProjectStatusIdComponent,
    ProjectStatusIdUpdateComponent,
    ProjectStatusIdDeleteDialogComponent,
    ProjectStatusIdDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppProjectStatusIdModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
