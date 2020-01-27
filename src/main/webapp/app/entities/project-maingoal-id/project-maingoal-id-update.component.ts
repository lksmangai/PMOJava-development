import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IProjectMaingoalId, ProjectMaingoalId } from 'app/shared/model/project-maingoal-id.model';
import { ProjectMaingoalIdService } from './project-maingoal-id.service';

@Component({
  selector: 'jhi-project-maingoal-id-update',
  templateUrl: './project-maingoal-id-update.component.html'
})
export class ProjectMaingoalIdUpdateComponent implements OnInit {
  projectMaingoalId: IProjectMaingoalId;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [],
    name: [],
    description: []
  });

  constructor(
    protected projectMaingoalIdService: ProjectMaingoalIdService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ projectMaingoalId }) => {
      this.updateForm(projectMaingoalId);
      this.projectMaingoalId = projectMaingoalId;
    });
  }

  updateForm(projectMaingoalId: IProjectMaingoalId) {
    this.editForm.patchValue({
      id: projectMaingoalId.id,
      code: projectMaingoalId.code,
      name: projectMaingoalId.name,
      description: projectMaingoalId.description
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const projectMaingoalId = this.createFromForm();
    if (projectMaingoalId.id !== undefined) {
      this.subscribeToSaveResponse(this.projectMaingoalIdService.update(projectMaingoalId));
    } else {
      this.subscribeToSaveResponse(this.projectMaingoalIdService.create(projectMaingoalId));
    }
  }

  private createFromForm(): IProjectMaingoalId {
    const entity = {
      ...new ProjectMaingoalId(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProjectMaingoalId>>) {
    result.subscribe((res: HttpResponse<IProjectMaingoalId>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
