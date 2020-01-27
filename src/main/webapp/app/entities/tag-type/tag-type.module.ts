import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  TagTypeComponent,
  TagTypeDetailComponent,
  TagTypeUpdateComponent,
  TagTypeDeletePopupComponent,
  TagTypeDeleteDialogComponent,
  tagTypeRoute,
  tagTypePopupRoute
} from './';

const ENTITY_STATES = [...tagTypeRoute, ...tagTypePopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TagTypeComponent,
    TagTypeDetailComponent,
    TagTypeUpdateComponent,
    TagTypeDeleteDialogComponent,
    TagTypeDeletePopupComponent
  ],
  entryComponents: [TagTypeComponent, TagTypeUpdateComponent, TagTypeDeleteDialogComponent, TagTypeDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppTagTypeModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
