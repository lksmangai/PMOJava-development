import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISkillCategory } from 'app/shared/model/skill-category.model';

@Component({
  selector: 'jhi-skill-category-detail',
  templateUrl: './skill-category-detail.component.html'
})
export class SkillCategoryDetailComponent implements OnInit {
  skillCategory: ISkillCategory;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ skillCategory }) => {
      this.skillCategory = skillCategory;
    });
  }

  previousState() {
    window.history.back();
  }
}
