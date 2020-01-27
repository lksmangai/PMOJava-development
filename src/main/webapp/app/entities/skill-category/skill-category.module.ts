import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  SkillCategoryComponent,
  SkillCategoryDetailComponent,
  SkillCategoryUpdateComponent,
  SkillCategoryDeletePopupComponent,
  SkillCategoryDeleteDialogComponent,
  skillCategoryRoute,
  skillCategoryPopupRoute
} from './';

const ENTITY_STATES = [...skillCategoryRoute, ...skillCategoryPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SkillCategoryComponent,
    SkillCategoryDetailComponent,
    SkillCategoryUpdateComponent,
    SkillCategoryDeleteDialogComponent,
    SkillCategoryDeletePopupComponent
  ],
  entryComponents: [
    SkillCategoryComponent,
    SkillCategoryUpdateComponent,
    SkillCategoryDeleteDialogComponent,
    SkillCategoryDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppSkillCategoryModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
