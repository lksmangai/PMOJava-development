import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  ProjectThemeComponent,
  ProjectThemeDetailComponent,
  ProjectThemeUpdateComponent,
  ProjectThemeDeletePopupComponent,
  ProjectThemeDeleteDialogComponent,
  projectThemeRoute,
  projectThemePopupRoute
} from './';

const ENTITY_STATES = [...projectThemeRoute, ...projectThemePopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProjectThemeComponent,
    ProjectThemeDetailComponent,
    ProjectThemeUpdateComponent,
    ProjectThemeDeleteDialogComponent,
    ProjectThemeDeletePopupComponent
  ],
  entryComponents: [
    ProjectThemeComponent,
    ProjectThemeUpdateComponent,
    ProjectThemeDeleteDialogComponent,
    ProjectThemeDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppProjectThemeModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
