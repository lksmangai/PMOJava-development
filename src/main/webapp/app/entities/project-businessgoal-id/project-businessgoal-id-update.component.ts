import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IProjectBusinessgoalId, ProjectBusinessgoalId } from 'app/shared/model/project-businessgoal-id.model';
import { ProjectBusinessgoalIdService } from './project-businessgoal-id.service';

@Component({
  selector: 'jhi-project-businessgoal-id-update',
  templateUrl: './project-businessgoal-id-update.component.html'
})
export class ProjectBusinessgoalIdUpdateComponent implements OnInit {
  projectBusinessgoalId: IProjectBusinessgoalId;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [],
    name: [],
    description: []
  });

  constructor(
    protected projectBusinessgoalIdService: ProjectBusinessgoalIdService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ projectBusinessgoalId }) => {
      this.updateForm(projectBusinessgoalId);
      this.projectBusinessgoalId = projectBusinessgoalId;
    });
  }

  updateForm(projectBusinessgoalId: IProjectBusinessgoalId) {
    this.editForm.patchValue({
      id: projectBusinessgoalId.id,
      code: projectBusinessgoalId.code,
      name: projectBusinessgoalId.name,
      description: projectBusinessgoalId.description
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const projectBusinessgoalId = this.createFromForm();
    if (projectBusinessgoalId.id !== undefined) {
      this.subscribeToSaveResponse(this.projectBusinessgoalIdService.update(projectBusinessgoalId));
    } else {
      this.subscribeToSaveResponse(this.projectBusinessgoalIdService.create(projectBusinessgoalId));
    }
  }

  private createFromForm(): IProjectBusinessgoalId {
    const entity = {
      ...new ProjectBusinessgoalId(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProjectBusinessgoalId>>) {
    result.subscribe((res: HttpResponse<IProjectBusinessgoalId>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
