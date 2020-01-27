import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  ProjectInitiativeIdComponent,
  ProjectInitiativeIdDetailComponent,
  ProjectInitiativeIdUpdateComponent,
  ProjectInitiativeIdDeletePopupComponent,
  ProjectInitiativeIdDeleteDialogComponent,
  projectInitiativeIdRoute,
  projectInitiativeIdPopupRoute
} from './';

const ENTITY_STATES = [...projectInitiativeIdRoute, ...projectInitiativeIdPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProjectInitiativeIdComponent,
    ProjectInitiativeIdDetailComponent,
    ProjectInitiativeIdUpdateComponent,
    ProjectInitiativeIdDeleteDialogComponent,
    ProjectInitiativeIdDeletePopupComponent
  ],
  entryComponents: [
    ProjectInitiativeIdComponent,
    ProjectInitiativeIdUpdateComponent,
    ProjectInitiativeIdDeleteDialogComponent,
    ProjectInitiativeIdDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppProjectInitiativeIdModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
