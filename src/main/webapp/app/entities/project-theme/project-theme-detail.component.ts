import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProjectTheme } from 'app/shared/model/project-theme.model';

@Component({
  selector: 'jhi-project-theme-detail',
  templateUrl: './project-theme-detail.component.html'
})
export class ProjectThemeDetailComponent implements OnInit {
  projectTheme: IProjectTheme;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ projectTheme }) => {
      this.projectTheme = projectTheme;
    });
  }

  previousState() {
    window.history.back();
  }
}
