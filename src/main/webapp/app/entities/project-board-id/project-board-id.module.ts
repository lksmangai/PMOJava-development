import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  ProjectBoardIdComponent,
  ProjectBoardIdDetailComponent,
  ProjectBoardIdUpdateComponent,
  ProjectBoardIdDeletePopupComponent,
  ProjectBoardIdDeleteDialogComponent,
  projectBoardIdRoute,
  projectBoardIdPopupRoute
} from './';

const ENTITY_STATES = [...projectBoardIdRoute, ...projectBoardIdPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProjectBoardIdComponent,
    ProjectBoardIdDetailComponent,
    ProjectBoardIdUpdateComponent,
    ProjectBoardIdDeleteDialogComponent,
    ProjectBoardIdDeletePopupComponent
  ],
  entryComponents: [
    ProjectBoardIdComponent,
    ProjectBoardIdUpdateComponent,
    ProjectBoardIdDeleteDialogComponent,
    ProjectBoardIdDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppProjectBoardIdModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
