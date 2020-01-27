import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProjectBoardId } from 'app/shared/model/project-board-id.model';

@Component({
  selector: 'jhi-project-board-id-detail',
  templateUrl: './project-board-id-detail.component.html'
})
export class ProjectBoardIdDetailComponent implements OnInit {
  projectBoardId: IProjectBoardId;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ projectBoardId }) => {
      this.projectBoardId = projectBoardId;
    });
  }

  previousState() {
    window.history.back();
  }
}
