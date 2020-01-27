import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  ImProjectsComponent,
  ImProjectsDetailComponent,
  ImProjectsUpdateComponent,
  ImProjectsDeletePopupComponent,
  ImProjectsDeleteDialogComponent,
  imProjectsRoute,
  imProjectsPopupRoute
} from './';

const ENTITY_STATES = [...imProjectsRoute, ...imProjectsPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ImProjectsComponent,
    ImProjectsDetailComponent,
    ImProjectsUpdateComponent,
    ImProjectsDeleteDialogComponent,
    ImProjectsDeletePopupComponent
  ],
  entryComponents: [ImProjectsComponent, ImProjectsUpdateComponent, ImProjectsDeleteDialogComponent, ImProjectsDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppImProjectsModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
