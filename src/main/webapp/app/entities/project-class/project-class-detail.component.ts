import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProjectClass } from 'app/shared/model/project-class.model';

@Component({
  selector: 'jhi-project-class-detail',
  templateUrl: './project-class-detail.component.html'
})
export class ProjectClassDetailComponent implements OnInit {
  projectClass: IProjectClass;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ projectClass }) => {
      this.projectClass = projectClass;
    });
  }

  previousState() {
    window.history.back();
  }
}
