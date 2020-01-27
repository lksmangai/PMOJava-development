import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProjectBusinessgoalId } from 'app/shared/model/project-businessgoal-id.model';

@Component({
  selector: 'jhi-project-businessgoal-id-detail',
  templateUrl: './project-businessgoal-id-detail.component.html'
})
export class ProjectBusinessgoalIdDetailComponent implements OnInit {
  projectBusinessgoalId: IProjectBusinessgoalId;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ projectBusinessgoalId }) => {
      this.projectBusinessgoalId = projectBusinessgoalId;
    });
  }

  previousState() {
    window.history.back();
  }
}
