import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IProjectRoles, ProjectRoles } from 'app/shared/model/project-roles.model';
import { ProjectRolesService } from './project-roles.service';

@Component({
  selector: 'jhi-project-roles-update',
  templateUrl: './project-roles-update.component.html'
})
export class ProjectRolesUpdateComponent implements OnInit {
  projectRoles: IProjectRoles;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [],
    name: [],
    description: []
  });

  constructor(protected projectRolesService: ProjectRolesService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ projectRoles }) => {
      this.updateForm(projectRoles);
      this.projectRoles = projectRoles;
    });
  }

  updateForm(projectRoles: IProjectRoles) {
    this.editForm.patchValue({
      id: projectRoles.id,
      code: projectRoles.code,
      name: projectRoles.name,
      description: projectRoles.description
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const projectRoles = this.createFromForm();
    if (projectRoles.id !== undefined) {
      this.subscribeToSaveResponse(this.projectRolesService.update(projectRoles));
    } else {
      this.subscribeToSaveResponse(this.projectRolesService.create(projectRoles));
    }
  }

  private createFromForm(): IProjectRoles {
    const entity = {
      ...new ProjectRoles(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProjectRoles>>) {
    result.subscribe((res: HttpResponse<IProjectRoles>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
