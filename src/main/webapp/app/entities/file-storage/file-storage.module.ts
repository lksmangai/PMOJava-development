import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  FileStorageComponent,
  FileStorageDetailComponent,
  FileStorageUpdateComponent,
  FileStorageDeletePopupComponent,
  FileStorageDeleteDialogComponent,
  fileStorageRoute,
  fileStoragePopupRoute
} from './';

const ENTITY_STATES = [...fileStorageRoute, ...fileStoragePopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    FileStorageComponent,
    FileStorageDetailComponent,
    FileStorageUpdateComponent,
    FileStorageDeleteDialogComponent,
    FileStorageDeletePopupComponent
  ],
  entryComponents: [FileStorageComponent, FileStorageUpdateComponent, FileStorageDeleteDialogComponent, FileStorageDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppFileStorageModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
