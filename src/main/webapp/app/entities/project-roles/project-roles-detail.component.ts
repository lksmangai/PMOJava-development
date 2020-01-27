import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProjectRoles } from 'app/shared/model/project-roles.model';

@Component({
  selector: 'jhi-project-roles-detail',
  templateUrl: './project-roles-detail.component.html'
})
export class ProjectRolesDetailComponent implements OnInit {
  projectRoles: IProjectRoles;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ projectRoles }) => {
      this.projectRoles = projectRoles;
    });
  }

  previousState() {
    window.history.back();
  }
}
