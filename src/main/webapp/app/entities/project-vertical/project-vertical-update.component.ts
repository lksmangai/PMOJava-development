import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IProjectVertical, ProjectVertical } from 'app/shared/model/project-vertical.model';
import { ProjectVerticalService } from './project-vertical.service';

@Component({
  selector: 'jhi-project-vertical-update',
  templateUrl: './project-vertical-update.component.html'
})
export class ProjectVerticalUpdateComponent implements OnInit {
  projectVertical: IProjectVertical;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [],
    name: [],
    description: []
  });

  constructor(
    protected projectVerticalService: ProjectVerticalService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ projectVertical }) => {
      this.updateForm(projectVertical);
      this.projectVertical = projectVertical;
    });
  }

  updateForm(projectVertical: IProjectVertical) {
    this.editForm.patchValue({
      id: projectVertical.id,
      code: projectVertical.code,
      name: projectVertical.name,
      description: projectVertical.description
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const projectVertical = this.createFromForm();
    if (projectVertical.id !== undefined) {
      this.subscribeToSaveResponse(this.projectVerticalService.update(projectVertical));
    } else {
      this.subscribeToSaveResponse(this.projectVerticalService.create(projectVertical));
    }
  }

  private createFromForm(): IProjectVertical {
    const entity = {
      ...new ProjectVertical(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProjectVertical>>) {
    result.subscribe((res: HttpResponse<IProjectVertical>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
