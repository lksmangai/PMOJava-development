import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProjectTypeId } from 'app/shared/model/project-type-id.model';

@Component({
  selector: 'jhi-project-type-id-detail',
  templateUrl: './project-type-id-detail.component.html'
})
export class ProjectTypeIdDetailComponent implements OnInit {
  projectTypeId: IProjectTypeId;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ projectTypeId }) => {
      this.projectTypeId = projectTypeId;
    });
  }

  previousState() {
    window.history.back();
  }
}
