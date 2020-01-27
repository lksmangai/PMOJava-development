import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProjectInitiativeId } from 'app/shared/model/project-initiative-id.model';

@Component({
  selector: 'jhi-project-initiative-id-detail',
  templateUrl: './project-initiative-id-detail.component.html'
})
export class ProjectInitiativeIdDetailComponent implements OnInit {
  projectInitiativeId: IProjectInitiativeId;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ projectInitiativeId }) => {
      this.projectInitiativeId = projectInitiativeId;
    });
  }

  previousState() {
    window.history.back();
  }
}
