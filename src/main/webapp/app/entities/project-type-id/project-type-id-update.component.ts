import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IProjectTypeId, ProjectTypeId } from 'app/shared/model/project-type-id.model';
import { ProjectTypeIdService } from './project-type-id.service';

@Component({
  selector: 'jhi-project-type-id-update',
  templateUrl: './project-type-id-update.component.html'
})
export class ProjectTypeIdUpdateComponent implements OnInit {
  projectTypeId: IProjectTypeId;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [],
    name: [],
    description: []
  });

  constructor(protected projectTypeIdService: ProjectTypeIdService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ projectTypeId }) => {
      this.updateForm(projectTypeId);
      this.projectTypeId = projectTypeId;
    });
  }

  updateForm(projectTypeId: IProjectTypeId) {
    this.editForm.patchValue({
      id: projectTypeId.id,
      code: projectTypeId.code,
      name: projectTypeId.name,
      description: projectTypeId.description
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const projectTypeId = this.createFromForm();
    if (projectTypeId.id !== undefined) {
      this.subscribeToSaveResponse(this.projectTypeIdService.update(projectTypeId));
    } else {
      this.subscribeToSaveResponse(this.projectTypeIdService.create(projectTypeId));
    }
  }

  private createFromForm(): IProjectTypeId {
    const entity = {
      ...new ProjectTypeId(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProjectTypeId>>) {
    result.subscribe((res: HttpResponse<IProjectTypeId>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
