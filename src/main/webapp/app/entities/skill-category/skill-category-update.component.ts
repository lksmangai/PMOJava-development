import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ISkillCategory, SkillCategory } from 'app/shared/model/skill-category.model';
import { SkillCategoryService } from './skill-category.service';

@Component({
  selector: 'jhi-skill-category-update',
  templateUrl: './skill-category-update.component.html'
})
export class SkillCategoryUpdateComponent implements OnInit {
  skillCategory: ISkillCategory;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    skillCategoryCode: [],
    skillCategoryName: []
  });

  constructor(protected skillCategoryService: SkillCategoryService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ skillCategory }) => {
      this.updateForm(skillCategory);
      this.skillCategory = skillCategory;
    });
  }

  updateForm(skillCategory: ISkillCategory) {
    this.editForm.patchValue({
      id: skillCategory.id,
      skillCategoryCode: skillCategory.skillCategoryCode,
      skillCategoryName: skillCategory.skillCategoryName
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const skillCategory = this.createFromForm();
    if (skillCategory.id !== undefined) {
      this.subscribeToSaveResponse(this.skillCategoryService.update(skillCategory));
    } else {
      this.subscribeToSaveResponse(this.skillCategoryService.create(skillCategory));
    }
  }

  private createFromForm(): ISkillCategory {
    const entity = {
      ...new SkillCategory(),
      id: this.editForm.get(['id']).value,
      skillCategoryCode: this.editForm.get(['skillCategoryCode']).value,
      skillCategoryName: this.editForm.get(['skillCategoryName']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISkillCategory>>) {
    result.subscribe((res: HttpResponse<ISkillCategory>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
