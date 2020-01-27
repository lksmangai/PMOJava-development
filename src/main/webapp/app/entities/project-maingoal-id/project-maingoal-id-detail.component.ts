import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProjectMaingoalId } from 'app/shared/model/project-maingoal-id.model';

@Component({
  selector: 'jhi-project-maingoal-id-detail',
  templateUrl: './project-maingoal-id-detail.component.html'
})
export class ProjectMaingoalIdDetailComponent implements OnInit {
  projectMaingoalId: IProjectMaingoalId;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ projectMaingoalId }) => {
      this.projectMaingoalId = projectMaingoalId;
    });
  }

  previousState() {
    window.history.back();
  }
}
