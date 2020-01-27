import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IProjectBoardId, ProjectBoardId } from 'app/shared/model/project-board-id.model';
import { ProjectBoardIdService } from './project-board-id.service';

@Component({
  selector: 'jhi-project-board-id-update',
  templateUrl: './project-board-id-update.component.html'
})
export class ProjectBoardIdUpdateComponent implements OnInit {
  projectBoardId: IProjectBoardId;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [],
    name: [],
    description: []
  });

  constructor(protected projectBoardIdService: ProjectBoardIdService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ projectBoardId }) => {
      this.updateForm(projectBoardId);
      this.projectBoardId = projectBoardId;
    });
  }

  updateForm(projectBoardId: IProjectBoardId) {
    this.editForm.patchValue({
      id: projectBoardId.id,
      code: projectBoardId.code,
      name: projectBoardId.name,
      description: projectBoardId.description
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const projectBoardId = this.createFromForm();
    if (projectBoardId.id !== undefined) {
      this.subscribeToSaveResponse(this.projectBoardIdService.update(projectBoardId));
    } else {
      this.subscribeToSaveResponse(this.projectBoardIdService.create(projectBoardId));
    }
  }

  private createFromForm(): IProjectBoardId {
    const entity = {
      ...new ProjectBoardId(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProjectBoardId>>) {
    result.subscribe((res: HttpResponse<IProjectBoardId>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
