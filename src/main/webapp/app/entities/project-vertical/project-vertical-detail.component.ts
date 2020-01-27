import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProjectVertical } from 'app/shared/model/project-vertical.model';

@Component({
  selector: 'jhi-project-vertical-detail',
  templateUrl: './project-vertical-detail.component.html'
})
export class ProjectVerticalDetailComponent implements OnInit {
  projectVertical: IProjectVertical;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ projectVertical }) => {
      this.projectVertical = projectVertical;
    });
  }

  previousState() {
    window.history.back();
  }
}
