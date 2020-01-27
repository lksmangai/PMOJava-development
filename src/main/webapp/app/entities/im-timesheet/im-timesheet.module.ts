import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  ImTimesheetComponent,
  ImTimesheetDetailComponent,
  ImTimesheetUpdateComponent,
  ImTimesheetDeletePopupComponent,
  ImTimesheetDeleteDialogComponent,
  imTimesheetRoute,
  imTimesheetPopupRoute
} from './';

const ENTITY_STATES = [...imTimesheetRoute, ...imTimesheetPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ImTimesheetComponent,
    ImTimesheetDetailComponent,
    ImTimesheetUpdateComponent,
    ImTimesheetDeleteDialogComponent,
    ImTimesheetDeletePopupComponent
  ],
  entryComponents: [ImTimesheetComponent, ImTimesheetUpdateComponent, ImTimesheetDeleteDialogComponent, ImTimesheetDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppImTimesheetModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
