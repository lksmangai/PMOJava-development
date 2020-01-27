import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  ProjectAllocationComponent,
  ProjectAllocationDetailComponent,
  ProjectAllocationUpdateComponent,
  ProjectAllocationDeletePopupComponent,
  ProjectAllocationDeleteDialogComponent,
  projectAllocationRoute,
  projectAllocationPopupRoute
} from './';

const ENTITY_STATES = [...projectAllocationRoute, ...projectAllocationPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProjectAllocationComponent,
    ProjectAllocationDetailComponent,
    ProjectAllocationUpdateComponent,
    ProjectAllocationDeleteDialogComponent,
    ProjectAllocationDeletePopupComponent
  ],
  entryComponents: [
    ProjectAllocationComponent,
    ProjectAllocationUpdateComponent,
    ProjectAllocationDeleteDialogComponent,
    ProjectAllocationDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppProjectAllocationModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
