import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  UserContactComponent,
  UserContactDetailComponent,
  UserContactUpdateComponent,
  UserContactDeletePopupComponent,
  UserContactDeleteDialogComponent,
  userContactRoute,
  userContactPopupRoute
} from './';

const ENTITY_STATES = [...userContactRoute, ...userContactPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    UserContactComponent,
    UserContactDetailComponent,
    UserContactUpdateComponent,
    UserContactDeleteDialogComponent,
    UserContactDeletePopupComponent
  ],
  entryComponents: [UserContactComponent, UserContactUpdateComponent, UserContactDeleteDialogComponent, UserContactDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppUserContactModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
