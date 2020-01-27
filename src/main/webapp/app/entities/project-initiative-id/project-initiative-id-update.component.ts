import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IProjectInitiativeId, ProjectInitiativeId } from 'app/shared/model/project-initiative-id.model';
import { ProjectInitiativeIdService } from './project-initiative-id.service';

@Component({
  selector: 'jhi-project-initiative-id-update',
  templateUrl: './project-initiative-id-update.component.html'
})
export class ProjectInitiativeIdUpdateComponent implements OnInit {
  projectInitiativeId: IProjectInitiativeId;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [],
    name: [],
    description: []
  });

  constructor(
    protected projectInitiativeIdService: ProjectInitiativeIdService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ projectInitiativeId }) => {
      this.updateForm(projectInitiativeId);
      this.projectInitiativeId = projectInitiativeId;
    });
  }

  updateForm(projectInitiativeId: IProjectInitiativeId) {
    this.editForm.patchValue({
      id: projectInitiativeId.id,
      code: projectInitiativeId.code,
      name: projectInitiativeId.name,
      description: projectInitiativeId.description
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const projectInitiativeId = this.createFromForm();
    if (projectInitiativeId.id !== undefined) {
      this.subscribeToSaveResponse(this.projectInitiativeIdService.update(projectInitiativeId));
    } else {
      this.subscribeToSaveResponse(this.projectInitiativeIdService.create(projectInitiativeId));
    }
  }

  private createFromForm(): IProjectInitiativeId {
    const entity = {
      ...new ProjectInitiativeId(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProjectInitiativeId>>) {
    result.subscribe((res: HttpResponse<IProjectInitiativeId>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
