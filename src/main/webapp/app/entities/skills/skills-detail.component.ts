import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISkills } from 'app/shared/model/skills.model';

@Component({
  selector: 'jhi-skills-detail',
  templateUrl: './skills-detail.component.html'
})
export class SkillsDetailComponent implements OnInit {
  skills: ISkills;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ skills }) => {
      this.skills = skills;
    });
  }

  previousState() {
    window.history.back();
  }
}
