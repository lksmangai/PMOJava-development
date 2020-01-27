import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IProjectStatusId, ProjectStatusId } from 'app/shared/model/project-status-id.model';
import { ProjectStatusIdService } from './project-status-id.service';

@Component({
  selector: 'jhi-project-status-id-update',
  templateUrl: './project-status-id-update.component.html'
})
export class ProjectStatusIdUpdateComponent implements OnInit {
  projectStatusId: IProjectStatusId;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [],
    name: [],
    description: []
  });

  constructor(
    protected projectStatusIdService: ProjectStatusIdService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ projectStatusId }) => {
      this.updateForm(projectStatusId);
      this.projectStatusId = projectStatusId;
    });
  }

  updateForm(projectStatusId: IProjectStatusId) {
    this.editForm.patchValue({
      id: projectStatusId.id,
      code: projectStatusId.code,
      name: projectStatusId.name,
      description: projectStatusId.description
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const projectStatusId = this.createFromForm();
    if (projectStatusId.id !== undefined) {
      this.subscribeToSaveResponse(this.projectStatusIdService.update(projectStatusId));
    } else {
      this.subscribeToSaveResponse(this.projectStatusIdService.create(projectStatusId));
    }
  }

  private createFromForm(): IProjectStatusId {
    const entity = {
      ...new ProjectStatusId(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProjectStatusId>>) {
    result.subscribe((res: HttpResponse<IProjectStatusId>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
