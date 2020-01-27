import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProjectCostCenterId } from 'app/shared/model/project-cost-center-id.model';

@Component({
  selector: 'jhi-project-cost-center-id-detail',
  templateUrl: './project-cost-center-id-detail.component.html'
})
export class ProjectCostCenterIdDetailComponent implements OnInit {
  projectCostCenterId: IProjectCostCenterId;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ projectCostCenterId }) => {
      this.projectCostCenterId = projectCostCenterId;
    });
  }

  previousState() {
    window.history.back();
  }
}
