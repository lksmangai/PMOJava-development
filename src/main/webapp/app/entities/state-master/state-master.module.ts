import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  StateMasterComponent,
  StateMasterDetailComponent,
  StateMasterUpdateComponent,
  StateMasterDeletePopupComponent,
  StateMasterDeleteDialogComponent,
  stateMasterRoute,
  stateMasterPopupRoute
} from './';

const ENTITY_STATES = [...stateMasterRoute, ...stateMasterPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    StateMasterComponent,
    StateMasterDetailComponent,
    StateMasterUpdateComponent,
    StateMasterDeleteDialogComponent,
    StateMasterDeletePopupComponent
  ],
  entryComponents: [StateMasterComponent, StateMasterUpdateComponent, StateMasterDeleteDialogComponent, StateMasterDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppStateMasterModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
