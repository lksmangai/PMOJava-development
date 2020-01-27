import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  SkillExpertiseComponent,
  SkillExpertiseDetailComponent,
  SkillExpertiseUpdateComponent,
  SkillExpertiseDeletePopupComponent,
  SkillExpertiseDeleteDialogComponent,
  skillExpertiseRoute,
  skillExpertisePopupRoute
} from './';

const ENTITY_STATES = [...skillExpertiseRoute, ...skillExpertisePopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SkillExpertiseComponent,
    SkillExpertiseDetailComponent,
    SkillExpertiseUpdateComponent,
    SkillExpertiseDeleteDialogComponent,
    SkillExpertiseDeletePopupComponent
  ],
  entryComponents: [
    SkillExpertiseComponent,
    SkillExpertiseUpdateComponent,
    SkillExpertiseDeleteDialogComponent,
    SkillExpertiseDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppSkillExpertiseModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
