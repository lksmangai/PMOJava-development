import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  ImEmployeeComponent,
  ImEmployeeDetailComponent,
  ImEmployeeUpdateComponent,
  ImEmployeeDeletePopupComponent,
  ImEmployeeDeleteDialogComponent,
  imEmployeeRoute,
  imEmployeePopupRoute
} from './';

const ENTITY_STATES = [...imEmployeeRoute, ...imEmployeePopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ImEmployeeComponent,
    ImEmployeeDetailComponent,
    ImEmployeeUpdateComponent,
    ImEmployeeDeleteDialogComponent,
    ImEmployeeDeletePopupComponent
  ],
  entryComponents: [ImEmployeeComponent, ImEmployeeUpdateComponent, ImEmployeeDeleteDialogComponent, ImEmployeeDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppImEmployeeModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
