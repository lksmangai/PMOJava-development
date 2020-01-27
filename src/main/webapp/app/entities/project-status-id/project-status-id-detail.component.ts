import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProjectStatusId } from 'app/shared/model/project-status-id.model';

@Component({
  selector: 'jhi-project-status-id-detail',
  templateUrl: './project-status-id-detail.component.html'
})
export class ProjectStatusIdDetailComponent implements OnInit {
  projectStatusId: IProjectStatusId;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ projectStatusId }) => {
      this.projectStatusId = projectStatusId;
    });
  }

  previousState() {
    window.history.back();
  }
}
