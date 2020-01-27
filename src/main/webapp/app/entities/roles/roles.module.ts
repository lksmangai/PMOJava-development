import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  RolesComponent,
  RolesDetailComponent,
  RolesUpdateComponent,
  RolesDeletePopupComponent,
  RolesDeleteDialogComponent,
  rolesRoute,
  rolesPopupRoute
} from './';

const ENTITY_STATES = [...rolesRoute, ...rolesPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [RolesComponent, RolesDetailComponent, RolesUpdateComponent, RolesDeleteDialogComponent, RolesDeletePopupComponent],
  entryComponents: [RolesComponent, RolesUpdateComponent, RolesDeleteDialogComponent, RolesDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppRolesModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
