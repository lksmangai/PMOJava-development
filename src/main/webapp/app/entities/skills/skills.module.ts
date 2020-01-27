import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  SkillsComponent,
  SkillsDetailComponent,
  SkillsUpdateComponent,
  SkillsDeletePopupComponent,
  SkillsDeleteDialogComponent,
  skillsRoute,
  skillsPopupRoute
} from './';

const ENTITY_STATES = [...skillsRoute, ...skillsPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [SkillsComponent, SkillsDetailComponent, SkillsUpdateComponent, SkillsDeleteDialogComponent, SkillsDeletePopupComponent],
  entryComponents: [SkillsComponent, SkillsUpdateComponent, SkillsDeleteDialogComponent, SkillsDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppSkillsModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
