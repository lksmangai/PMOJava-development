import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISkillExpertise } from 'app/shared/model/skill-expertise.model';

@Component({
  selector: 'jhi-skill-expertise-detail',
  templateUrl: './skill-expertise-detail.component.html'
})
export class SkillExpertiseDetailComponent implements OnInit {
  skillExpertise: ISkillExpertise;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ skillExpertise }) => {
      this.skillExpertise = skillExpertise;
    });
  }

  previousState() {
    window.history.back();
  }
}
