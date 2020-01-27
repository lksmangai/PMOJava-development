import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  OpportunityPriorityIdComponent,
  OpportunityPriorityIdDetailComponent,
  OpportunityPriorityIdUpdateComponent,
  OpportunityPriorityIdDeletePopupComponent,
  OpportunityPriorityIdDeleteDialogComponent,
  opportunityPriorityIdRoute,
  opportunityPriorityIdPopupRoute
} from './';

const ENTITY_STATES = [...opportunityPriorityIdRoute, ...opportunityPriorityIdPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    OpportunityPriorityIdComponent,
    OpportunityPriorityIdDetailComponent,
    OpportunityPriorityIdUpdateComponent,
    OpportunityPriorityIdDeleteDialogComponent,
    OpportunityPriorityIdDeletePopupComponent
  ],
  entryComponents: [
    OpportunityPriorityIdComponent,
    OpportunityPriorityIdUpdateComponent,
    OpportunityPriorityIdDeleteDialogComponent,
    OpportunityPriorityIdDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppOpportunityPriorityIdModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
