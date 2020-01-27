import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IProjectClass, ProjectClass } from 'app/shared/model/project-class.model';
import { ProjectClassService } from './project-class.service';

@Component({
  selector: 'jhi-project-class-update',
  templateUrl: './project-class-update.component.html'
})
export class ProjectClassUpdateComponent implements OnInit {
  projectClass: IProjectClass;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [],
    name: [],
    description: []
  });

  constructor(protected projectClassService: ProjectClassService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ projectClass }) => {
      this.updateForm(projectClass);
      this.projectClass = projectClass;
    });
  }

  updateForm(projectClass: IProjectClass) {
    this.editForm.patchValue({
      id: projectClass.id,
      code: projectClass.code,
      name: projectClass.name,
      description: projectClass.description
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const projectClass = this.createFromForm();
    if (projectClass.id !== undefined) {
      this.subscribeToSaveResponse(this.projectClassService.update(projectClass));
    } else {
      this.subscribeToSaveResponse(this.projectClassService.create(projectClass));
    }
  }

  private createFromForm(): IProjectClass {
    const entity = {
      ...new ProjectClass(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProjectClass>>) {
    result.subscribe((res: HttpResponse<IProjectClass>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
