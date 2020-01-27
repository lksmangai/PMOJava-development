import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProjectSubgoalId } from 'app/shared/model/project-subgoal-id.model';

@Component({
  selector: 'jhi-project-subgoal-id-detail',
  templateUrl: './project-subgoal-id-detail.component.html'
})
export class ProjectSubgoalIdDetailComponent implements OnInit {
  projectSubgoalId: IProjectSubgoalId;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ projectSubgoalId }) => {
      this.projectSubgoalId = projectSubgoalId;
    });
  }

  previousState() {
    window.history.back();
  }
}
