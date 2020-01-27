import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ISkills, Skills } from 'app/shared/model/skills.model';
import { SkillsService } from './skills.service';
import { ISkillCategory } from 'app/shared/model/skill-category.model';
import { SkillCategoryService } from 'app/entities/skill-category';

@Component({
  selector: 'jhi-skills-update',
  templateUrl: './skills-update.component.html'
})
export class SkillsUpdateComponent implements OnInit {
  skills: ISkills;
  isSaving: boolean;

  skillsCollection: ISkills[];

  skillcategories: ISkillCategory[];

  editForm = this.fb.group({
    id: [],
    skillCode: [],
    skillName: [],
    parentSkillsId: [],
    skillCategoryId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected skillsService: SkillsService,
    protected skillCategoryService: SkillCategoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ skills }) => {
      this.updateForm(skills);
      this.skills = skills;
    });
    this.skillsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISkills[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISkills[]>) => response.body)
      )
      .subscribe((res: ISkills[]) => (this.skillsCollection = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.skillCategoryService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISkillCategory[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISkillCategory[]>) => response.body)
      )
      .subscribe((res: ISkillCategory[]) => (this.skillcategories = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(skills: ISkills) {
    this.editForm.patchValue({
      id: skills.id,
      skillCode: skills.skillCode,
      skillName: skills.skillName,
      parentSkillsId: skills.parentSkillsId,
      skillCategoryId: skills.skillCategoryId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const skills = this.createFromForm();
    if (skills.id !== undefined) {
      this.subscribeToSaveResponse(this.skillsService.update(skills));
    } else {
      this.subscribeToSaveResponse(this.skillsService.create(skills));
    }
  }

  private createFromForm(): ISkills {
    const entity = {
      ...new Skills(),
      id: this.editForm.get(['id']).value,
      skillCode: this.editForm.get(['skillCode']).value,
      skillName: this.editForm.get(['skillName']).value,
      parentSkillsId: this.editForm.get(['parentSkillsId']).value,
      skillCategoryId: this.editForm.get(['skillCategoryId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISkills>>) {
    result.subscribe((res: HttpResponse<ISkills>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackSkillsById(index: number, item: ISkills) {
    return item.id;
  }

  trackSkillCategoryById(index: number, item: ISkillCategory) {
    return item.id;
  }
}
