import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IProjectTheme, ProjectTheme } from 'app/shared/model/project-theme.model';
import { ProjectThemeService } from './project-theme.service';

@Component({
  selector: 'jhi-project-theme-update',
  templateUrl: './project-theme-update.component.html'
})
export class ProjectThemeUpdateComponent implements OnInit {
  projectTheme: IProjectTheme;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [],
    name: [],
    description: []
  });

  constructor(protected projectThemeService: ProjectThemeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ projectTheme }) => {
      this.updateForm(projectTheme);
      this.projectTheme = projectTheme;
    });
  }

  updateForm(projectTheme: IProjectTheme) {
    this.editForm.patchValue({
      id: projectTheme.id,
      code: projectTheme.code,
      name: projectTheme.name,
      description: projectTheme.description
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const projectTheme = this.createFromForm();
    if (projectTheme.id !== undefined) {
      this.subscribeToSaveResponse(this.projectThemeService.update(projectTheme));
    } else {
      this.subscribeToSaveResponse(this.projectThemeService.create(projectTheme));
    }
  }

  private createFromForm(): IProjectTheme {
    const entity = {
      ...new ProjectTheme(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProjectTheme>>) {
    result.subscribe((res: HttpResponse<IProjectTheme>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
