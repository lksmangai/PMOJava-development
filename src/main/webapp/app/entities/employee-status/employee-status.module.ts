import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  EmployeeStatusComponent,
  EmployeeStatusDetailComponent,
  EmployeeStatusUpdateComponent,
  EmployeeStatusDeletePopupComponent,
  EmployeeStatusDeleteDialogComponent,
  employeeStatusRoute,
  employeeStatusPopupRoute
} from './';

const ENTITY_STATES = [...employeeStatusRoute, ...employeeStatusPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    EmployeeStatusComponent,
    EmployeeStatusDetailComponent,
    EmployeeStatusUpdateComponent,
    EmployeeStatusDeleteDialogComponent,
    EmployeeStatusDeletePopupComponent
  ],
  entryComponents: [
    EmployeeStatusComponent,
    EmployeeStatusUpdateComponent,
    EmployeeStatusDeleteDialogComponent,
    EmployeeStatusDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppEmployeeStatusModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
