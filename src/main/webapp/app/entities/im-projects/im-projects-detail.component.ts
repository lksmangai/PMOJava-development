import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IImProjects } from 'app/shared/model/im-projects.model';

@Component({
  selector: 'jhi-im-projects-detail',
  templateUrl: './im-projects-detail.component.html'
})
export class ImProjectsDetailComponent implements OnInit {
  imProjects: IImProjects;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ imProjects }) => {
      this.imProjects = imProjects;
    });
  }

  previousState() {
    window.history.back();
  }
}
