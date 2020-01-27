import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProjectAllocation } from 'app/shared/model/project-allocation.model';

@Component({
  selector: 'jhi-project-allocation-detail',
  templateUrl: './project-allocation-detail.component.html'
})
export class ProjectAllocationDetailComponent implements OnInit {
  projectAllocation: IProjectAllocation;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ projectAllocation }) => {
      this.projectAllocation = projectAllocation;
    });
  }

  previousState() {
    window.history.back();
  }
}
