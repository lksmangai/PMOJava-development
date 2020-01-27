import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  ProjectTagComponent,
  ProjectTagDetailComponent,
  ProjectTagUpdateComponent,
  ProjectTagDeletePopupComponent,
  ProjectTagDeleteDialogComponent,
  projectTagRoute,
  projectTagPopupRoute
} from './';

const ENTITY_STATES = [...projectTagRoute, ...projectTagPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProjectTagComponent,
    ProjectTagDetailComponent,
    ProjectTagUpdateComponent,
    ProjectTagDeleteDialogComponent,
    ProjectTagDeletePopupComponent
  ],
  entryComponents: [ProjectTagComponent, ProjectTagUpdateComponent, ProjectTagDeleteDialogComponent, ProjectTagDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppProjectTagModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
