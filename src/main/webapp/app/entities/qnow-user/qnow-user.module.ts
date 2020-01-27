import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  QnowUserComponent,
  QnowUserDetailComponent,
  QnowUserUpdateComponent,
  QnowUserDeletePopupComponent,
  QnowUserDeleteDialogComponent,
  qnowUserRoute,
  qnowUserPopupRoute
} from './';

const ENTITY_STATES = [...qnowUserRoute, ...qnowUserPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    QnowUserComponent,
    QnowUserDetailComponent,
    QnowUserUpdateComponent,
    QnowUserDeleteDialogComponent,
    QnowUserDeletePopupComponent
  ],
  entryComponents: [QnowUserComponent, QnowUserUpdateComponent, QnowUserDeleteDialogComponent, QnowUserDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppQnowUserModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
