import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IProjectBucketId, ProjectBucketId } from 'app/shared/model/project-bucket-id.model';
import { ProjectBucketIdService } from './project-bucket-id.service';

@Component({
  selector: 'jhi-project-bucket-id-update',
  templateUrl: './project-bucket-id-update.component.html'
})
export class ProjectBucketIdUpdateComponent implements OnInit {
  projectBucketId: IProjectBucketId;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [],
    name: [],
    description: []
  });

  constructor(
    protected projectBucketIdService: ProjectBucketIdService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ projectBucketId }) => {
      this.updateForm(projectBucketId);
      this.projectBucketId = projectBucketId;
    });
  }

  updateForm(projectBucketId: IProjectBucketId) {
    this.editForm.patchValue({
      id: projectBucketId.id,
      code: projectBucketId.code,
      name: projectBucketId.name,
      description: projectBucketId.description
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const projectBucketId = this.createFromForm();
    if (projectBucketId.id !== undefined) {
      this.subscribeToSaveResponse(this.projectBucketIdService.update(projectBucketId));
    } else {
      this.subscribeToSaveResponse(this.projectBucketIdService.create(projectBucketId));
    }
  }

  private createFromForm(): IProjectBucketId {
    const entity = {
      ...new ProjectBucketId(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProjectBucketId>>) {
    result.subscribe((res: HttpResponse<IProjectBucketId>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
