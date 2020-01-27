import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISkillTable } from 'app/shared/model/skill-table.model';

@Component({
  selector: 'jhi-skill-table-detail',
  templateUrl: './skill-table-detail.component.html'
})
export class SkillTableDetailComponent implements OnInit {
  skillTable: ISkillTable;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ skillTable }) => {
      this.skillTable = skillTable;
    });
  }

  previousState() {
    window.history.back();
  }
}
