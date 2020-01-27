import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IProjectSubgoalId, ProjectSubgoalId } from 'app/shared/model/project-subgoal-id.model';
import { ProjectSubgoalIdService } from './project-subgoal-id.service';

@Component({
  selector: 'jhi-project-subgoal-id-update',
  templateUrl: './project-subgoal-id-update.component.html'
})
export class ProjectSubgoalIdUpdateComponent implements OnInit {
  projectSubgoalId: IProjectSubgoalId;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [],
    name: [],
    description: []
  });

  constructor(
    protected projectSubgoalIdService: ProjectSubgoalIdService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ projectSubgoalId }) => {
      this.updateForm(projectSubgoalId);
      this.projectSubgoalId = projectSubgoalId;
    });
  }

  updateForm(projectSubgoalId: IProjectSubgoalId) {
    this.editForm.patchValue({
      id: projectSubgoalId.id,
      code: projectSubgoalId.code,
      name: projectSubgoalId.name,
      description: projectSubgoalId.description
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const projectSubgoalId = this.createFromForm();
    if (projectSubgoalId.id !== undefined) {
      this.subscribeToSaveResponse(this.projectSubgoalIdService.update(projectSubgoalId));
    } else {
      this.subscribeToSaveResponse(this.projectSubgoalIdService.create(projectSubgoalId));
    }
  }

  private createFromForm(): IProjectSubgoalId {
    const entity = {
      ...new ProjectSubgoalId(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProjectSubgoalId>>) {
    result.subscribe((res: HttpResponse<IProjectSubgoalId>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
