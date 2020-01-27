import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  ProjectMaingoalIdComponent,
  ProjectMaingoalIdDetailComponent,
  ProjectMaingoalIdUpdateComponent,
  ProjectMaingoalIdDeletePopupComponent,
  ProjectMaingoalIdDeleteDialogComponent,
  projectMaingoalIdRoute,
  projectMaingoalIdPopupRoute
} from './';

const ENTITY_STATES = [...projectMaingoalIdRoute, ...projectMaingoalIdPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProjectMaingoalIdComponent,
    ProjectMaingoalIdDetailComponent,
    ProjectMaingoalIdUpdateComponent,
    ProjectMaingoalIdDeleteDialogComponent,
    ProjectMaingoalIdDeletePopupComponent
  ],
  entryComponents: [
    ProjectMaingoalIdComponent,
    ProjectMaingoalIdUpdateComponent,
    ProjectMaingoalIdDeleteDialogComponent,
    ProjectMaingoalIdDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppProjectMaingoalIdModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
