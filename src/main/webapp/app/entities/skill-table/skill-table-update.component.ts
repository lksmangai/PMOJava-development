import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ISkillTable, SkillTable } from 'app/shared/model/skill-table.model';
import { SkillTableService } from './skill-table.service';
import { ISkillExpertise } from 'app/shared/model/skill-expertise.model';
import { SkillExpertiseService } from 'app/entities/skill-expertise';
import { IUserContact } from 'app/shared/model/user-contact.model';
import { UserContactService } from 'app/entities/user-contact';
import { IImProjects } from 'app/shared/model/im-projects.model';
import { ImProjectsService } from 'app/entities/im-projects';
import { ISkills } from 'app/shared/model/skills.model';
import { SkillsService } from 'app/entities/skills';

@Component({
  selector: 'jhi-skill-table-update',
  templateUrl: './skill-table-update.component.html'
})
export class SkillTableUpdateComponent implements OnInit {
  skillTable: ISkillTable;
  isSaving: boolean;

  skillexpertises: ISkillExpertise[];

  usercontacts: IUserContact[];

  improjects: IImProjects[];

  skills: ISkills[];

  editForm = this.fb.group({
    id: [],
    skillExpertise: [],
    userContact: [],
    imProjects: [],
    skills: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected skillTableService: SkillTableService,
    protected skillExpertiseService: SkillExpertiseService,
    protected userContactService: UserContactService,
    protected imProjectsService: ImProjectsService,
    protected skillsService: SkillsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ skillTable }) => {
      this.updateForm(skillTable);
      this.skillTable = skillTable;
    });
    this.skillExpertiseService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISkillExpertise[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISkillExpertise[]>) => response.body)
      )
      .subscribe((res: ISkillExpertise[]) => (this.skillexpertises = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.userContactService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUserContact[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUserContact[]>) => response.body)
      )
      .subscribe((res: IUserContact[]) => (this.usercontacts = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.imProjectsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IImProjects[]>) => mayBeOk.ok),
        map((response: HttpResponse<IImProjects[]>) => response.body)
      )
      .subscribe((res: IImProjects[]) => (this.improjects = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.skillsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISkills[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISkills[]>) => response.body)
      )
      .subscribe((res: ISkills[]) => (this.skills = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(skillTable: ISkillTable) {
    this.editForm.patchValue({
      id: skillTable.id,
      skillExpertise: skillTable.skillExpertise,
      userContact: skillTable.userContact,
      imProjects: skillTable.imProjects,
      skills: skillTable.skills
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const skillTable = this.createFromForm();
    if (skillTable.id !== undefined) {
      this.subscribeToSaveResponse(this.skillTableService.update(skillTable));
    } else {
      this.subscribeToSaveResponse(this.skillTableService.create(skillTable));
    }
  }

  private createFromForm(): ISkillTable {
    const entity = {
      ...new SkillTable(),
      id: this.editForm.get(['id']).value,
      skillExpertise: this.editForm.get(['skillExpertise']).value,
      userContact: this.editForm.get(['userContact']).value,
      imProjects: this.editForm.get(['imProjects']).value,
      skills: this.editForm.get(['skills']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISkillTable>>) {
    result.subscribe((res: HttpResponse<ISkillTable>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackSkillExpertiseById(index: number, item: ISkillExpertise) {
    return item.id;
  }

  trackUserContactById(index: number, item: IUserContact) {
    return item.id;
  }

  trackImProjectsById(index: number, item: IImProjects) {
    return item.id;
  }

  trackSkillsById(index: number, item: ISkills) {
    return item.id;
  }
}
