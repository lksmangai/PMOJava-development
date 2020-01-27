import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProjectTag, ProjectTag } from 'app/shared/model/project-tag.model';
import { ProjectTagService } from './project-tag.service';
import { ITagType } from 'app/shared/model/tag-type.model';
import { TagTypeService } from 'app/entities/tag-type';
import { IImEmployee } from 'app/shared/model/im-employee.model';
import { ImEmployeeService } from 'app/entities/im-employee';
import { IImProjects } from 'app/shared/model/im-projects.model';
import { ImProjectsService } from 'app/entities/im-projects';

@Component({
  selector: 'jhi-project-tag-update',
  templateUrl: './project-tag-update.component.html'
})
export class ProjectTagUpdateComponent implements OnInit {
  projectTag: IProjectTag;
  isSaving: boolean;

  tagtypes: ITagType[];

  imemployees: IImEmployee[];

  improjects: IImProjects[];

  editForm = this.fb.group({
    id: [],
    code: [],
    name: [],
    description: [],
    flag: [],
    color: [],
    tagType: [],
    imEmployee: [],
    imProjects: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected projectTagService: ProjectTagService,
    protected tagTypeService: TagTypeService,
    protected imEmployeeService: ImEmployeeService,
    protected imProjectsService: ImProjectsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ projectTag }) => {
      this.updateForm(projectTag);
      this.projectTag = projectTag;
    });
    this.tagTypeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITagType[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITagType[]>) => response.body)
      )
      .subscribe((res: ITagType[]) => (this.tagtypes = res), (res: HttpErrorResponse) => this.onError(res.message));
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
  }

  updateForm(projectTag: IProjectTag) {
    this.editForm.patchValue({
      id: projectTag.id,
      code: projectTag.code,
      name: projectTag.name,
      description: projectTag.description,
      flag: projectTag.flag,
      color: projectTag.color,
      tagType: projectTag.tagType,
      imEmployee: projectTag.imEmployee,
      imProjects: projectTag.imProjects
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const projectTag = this.createFromForm();
    if (projectTag.id !== undefined) {
      this.subscribeToSaveResponse(this.projectTagService.update(projectTag));
    } else {
      this.subscribeToSaveResponse(this.projectTagService.create(projectTag));
    }
  }

  private createFromForm(): IProjectTag {
    const entity = {
      ...new ProjectTag(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value,
      flag: this.editForm.get(['flag']).value,
      color: this.editForm.get(['color']).value,
      tagType: this.editForm.get(['tagType']).value,
      imEmployee: this.editForm.get(['imEmployee']).value,
      imProjects: this.editForm.get(['imProjects']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProjectTag>>) {
    result.subscribe((res: HttpResponse<IProjectTag>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackTagTypeById(index: number, item: ITagType) {
    return item.id;
  }

  trackImEmployeeById(index: number, item: IImEmployee) {
    return item.id;
  }

  trackImProjectsById(index: number, item: IImProjects) {
    return item.id;
  }
}
