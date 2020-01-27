import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  ProjectRolesComponent,
  ProjectRolesDetailComponent,
  ProjectRolesUpdateComponent,
  ProjectRolesDeletePopupComponent,
  ProjectRolesDeleteDialogComponent,
  projectRolesRoute,
  projectRolesPopupRoute
} from './';

const ENTITY_STATES = [...projectRolesRoute, ...projectRolesPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProjectRolesComponent,
    ProjectRolesDetailComponent,
    ProjectRolesUpdateComponent,
    ProjectRolesDeleteDialogComponent,
    ProjectRolesDeletePopupComponent
  ],
  entryComponents: [
    ProjectRolesComponent,
    ProjectRolesUpdateComponent,
    ProjectRolesDeleteDialogComponent,
    ProjectRolesDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppProjectRolesModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
