import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ISkillExpertise, SkillExpertise } from 'app/shared/model/skill-expertise.model';
import { SkillExpertiseService } from './skill-expertise.service';

@Component({
  selector: 'jhi-skill-expertise-update',
  templateUrl: './skill-expertise-update.component.html'
})
export class SkillExpertiseUpdateComponent implements OnInit {
  skillExpertise: ISkillExpertise;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [],
    name: [],
    description: []
  });

  constructor(protected skillExpertiseService: SkillExpertiseService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ skillExpertise }) => {
      this.updateForm(skillExpertise);
      this.skillExpertise = skillExpertise;
    });
  }

  updateForm(skillExpertise: ISkillExpertise) {
    this.editForm.patchValue({
      id: skillExpertise.id,
      code: skillExpertise.code,
      name: skillExpertise.name,
      description: skillExpertise.description
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const skillExpertise = this.createFromForm();
    if (skillExpertise.id !== undefined) {
      this.subscribeToSaveResponse(this.skillExpertiseService.update(skillExpertise));
    } else {
      this.subscribeToSaveResponse(this.skillExpertiseService.create(skillExpertise));
    }
  }

  private createFromForm(): ISkillExpertise {
    const entity = {
      ...new SkillExpertise(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISkillExpertise>>) {
    result.subscribe((res: HttpResponse<ISkillExpertise>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
