import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProjectBucketId } from 'app/shared/model/project-bucket-id.model';

@Component({
  selector: 'jhi-project-bucket-id-detail',
  templateUrl: './project-bucket-id-detail.component.html'
})
export class ProjectBucketIdDetailComponent implements OnInit {
  projectBucketId: IProjectBucketId;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ projectBucketId }) => {
      this.projectBucketId = projectBucketId;
    });
  }

  previousState() {
    window.history.back();
  }
}
