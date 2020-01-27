import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  SkillTableComponent,
  SkillTableDetailComponent,
  SkillTableUpdateComponent,
  SkillTableDeletePopupComponent,
  SkillTableDeleteDialogComponent,
  skillTableRoute,
  skillTablePopupRoute
} from './';

const ENTITY_STATES = [...skillTableRoute, ...skillTablePopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SkillTableComponent,
    SkillTableDetailComponent,
    SkillTableUpdateComponent,
    SkillTableDeleteDialogComponent,
    SkillTableDeletePopupComponent
  ],
  entryComponents: [SkillTableComponent, SkillTableUpdateComponent, SkillTableDeleteDialogComponent, SkillTableDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppSkillTableModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
