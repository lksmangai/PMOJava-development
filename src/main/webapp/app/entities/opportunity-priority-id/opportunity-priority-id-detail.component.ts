import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOpportunityPriorityId } from 'app/shared/model/opportunity-priority-id.model';

@Component({
  selector: 'jhi-opportunity-priority-id-detail',
  templateUrl: './opportunity-priority-id-detail.component.html'
})
export class OpportunityPriorityIdDetailComponent implements OnInit {
  opportunityPriorityId: IOpportunityPriorityId;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ opportunityPriorityId }) => {
      this.opportunityPriorityId = opportunityPriorityId;
    });
  }

  previousState() {
    window.history.back();
  }
}
