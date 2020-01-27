import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProjectAllocation, ProjectAllocation } from 'app/shared/model/project-allocation.model';
import { ProjectAllocationService } from './project-allocation.service';
import { IImEmployee } from 'app/shared/model/im-employee.model';
import { ImEmployeeService } from 'app/entities/im-employee';
import { IImProjects } from 'app/shared/model/im-projects.model';
import { ImProjectsService } from 'app/entities/im-projects';
import { IProjectRoles } from 'app/shared/model/project-roles.model';
import { ProjectRolesService } from 'app/entities/project-roles';

@Component({
  selector: 'jhi-project-allocation-update',
  templateUrl: './project-allocation-update.component.html'
})
export class ProjectAllocationUpdateComponent implements OnInit {
  projectAllocation: IProjectAllocation;
  isSaving: boolean;

  imemployees: IImEmployee[];

  improjects: IImProjects[];

  projectroles: IProjectRoles[];

  editForm = this.fb.group({
    id: [],
    percentage: [],
    imEmployee: [],
    imProjects: [],
    projectRoles: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected projectAllocationService: ProjectAllocationService,
    protected imEmployeeService: ImEmployeeService,
    protected imProjectsService: ImProjectsService,
    protected projectRolesService: ProjectRolesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ projectAllocation }) => {
      this.updateForm(projectAllocation);
      this.projectAllocation = projectAllocation;
    });
    this.imEmployeeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IImEmployee[]>) => mayBeOk.ok),
        map((response: HttpResponse<IImEmployee[]>) => response.body)
      )
      .subscribe((res: IImEmployee[]) => (this.imemployees = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.imProjectsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IImProjects[]>) => mayBeOk.ok),
        map((response: HttpResponse<IImProjects[]>) => response.body)
      )
      .subscribe((res: IImProjects[]) => (this.improjects = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.projectRolesService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProjectRoles[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProjectRoles[]>) => response.body)
      )
      .subscribe((res: IProjectRoles[]) => (this.projectroles = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(projectAllocation: IProjectAllocation) {
    this.editForm.patchValue({
      id: projectAllocation.id,
      percentage: projectAllocation.percentage,
      imEmployee: projectAllocation.imEmployee,
      imProjects: projectAllocation.imProjects,
      projectRoles: projectAllocation.projectRoles
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const projectAllocation = this.createFromForm();
    if (projectAllocation.id !== undefined) {
      this.subscribeToSaveResponse(this.projectAllocationService.update(projectAllocation));
    } else {
      this.subscribeToSaveResponse(this.projectAllocationService.create(projectAllocation));
    }
  }

  private createFromForm(): IProjectAllocation {
    const entity = {
      ...new ProjectAllocation(),
      id: this.editForm.get(['id']).value,
      percentage: this.editForm.get(['percentage']).value,
      imEmployee: this.editForm.get(['imEmployee']).value,
      imProjects: this.editForm.get(['imProjects']).value,
      projectRoles: this.editForm.get(['projectRoles']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProjectAllocation>>) {
    result.subscribe((res: HttpResponse<IProjectAllocation>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackImEmployeeById(index: number, item: IImEmployee) {
    return item.id;
  }

  trackImProjectsById(index: number, item: IImProjects) {
    return item.id;
  }

  trackProjectRolesById(index: number, item: IProjectRoles) {
    return item.id;
  }
}
