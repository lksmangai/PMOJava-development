import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProjectTag } from 'app/shared/model/project-tag.model';

@Component({
  selector: 'jhi-project-tag-detail',
  templateUrl: './project-tag-detail.component.html'
})
export class ProjectTagDetailComponent implements OnInit {
  projectTag: IProjectTag;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ projectTag }) => {
      this.projectTag = projectTag;
    });
  }

  previousState() {
    window.history.back();
  }
}
