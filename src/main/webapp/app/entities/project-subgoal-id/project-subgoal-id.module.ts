import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  ProjectSubgoalIdComponent,
  ProjectSubgoalIdDetailComponent,
  ProjectSubgoalIdUpdateComponent,
  ProjectSubgoalIdDeletePopupComponent,
  ProjectSubgoalIdDeleteDialogComponent,
  projectSubgoalIdRoute,
  projectSubgoalIdPopupRoute
} from './';

const ENTITY_STATES = [...projectSubgoalIdRoute, ...projectSubgoalIdPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProjectSubgoalIdComponent,
    ProjectSubgoalIdDetailComponent,
    ProjectSubgoalIdUpdateComponent,
    ProjectSubgoalIdDeleteDialogComponent,
    ProjectSubgoalIdDeletePopupComponent
  ],
  entryComponents: [
    ProjectSubgoalIdComponent,
    ProjectSubgoalIdUpdateComponent,
    ProjectSubgoalIdDeleteDialogComponent,
    ProjectSubgoalIdDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppProjectSubgoalIdModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
