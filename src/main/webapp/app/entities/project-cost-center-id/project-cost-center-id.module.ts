import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  ProjectCostCenterIdComponent,
  ProjectCostCenterIdDetailComponent,
  ProjectCostCenterIdUpdateComponent,
  ProjectCostCenterIdDeletePopupComponent,
  ProjectCostCenterIdDeleteDialogComponent,
  projectCostCenterIdRoute,
  projectCostCenterIdPopupRoute
} from './';

const ENTITY_STATES = [...projectCostCenterIdRoute, ...projectCostCenterIdPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProjectCostCenterIdComponent,
    ProjectCostCenterIdDetailComponent,
    ProjectCostCenterIdUpdateComponent,
    ProjectCostCenterIdDeleteDialogComponent,
    ProjectCostCenterIdDeletePopupComponent
  ],
  entryComponents: [
    ProjectCostCenterIdComponent,
    ProjectCostCenterIdUpdateComponent,
    ProjectCostCenterIdDeleteDialogComponent,
    ProjectCostCenterIdDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppProjectCostCenterIdModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
