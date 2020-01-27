import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IUserContact, UserContact } from 'app/shared/model/user-contact.model';
import { UserContactService } from './user-contact.service';
import { IQnowUser } from 'app/shared/model/qnow-user.model';
import { QnowUserService } from 'app/entities/qnow-user';
import { ICity } from 'app/shared/model/city.model';
import { CityService } from 'app/entities/city';
import { IStateMaster } from 'app/shared/model/state-master.model';
import { StateMasterService } from 'app/entities/state-master';
import { ICountry } from 'app/shared/model/country.model';
import { CountryService } from 'app/entities/country';
import { IGroupMembers } from 'app/shared/model/group-members.model';
import { GroupMembersService } from 'app/entities/group-members';
import { IImEmployee } from 'app/shared/model/im-employee.model';
import { ImEmployeeService } from 'app/entities/im-employee';

@Component({
  selector: 'jhi-user-contact-update',
  templateUrl: './user-contact-update.component.html'
})
export class UserContactUpdateComponent implements OnInit {
  userContact: IUserContact;
  isSaving: boolean;

  qnowusers: IQnowUser[];

  cities: ICity[];

  statemasters: IStateMaster[];

  countries: ICountry[];

  groupmembers: IGroupMembers[];

  imemployees: IImEmployee[];

  editForm = this.fb.group({
    id: [],
    homePhone: [],
    workPhone: [],
    cellPhone: [],
    permentAddress: [],
    haLine1: [],
    haLine2: [],
    haPostal: [],
    waLine1: [],
    waLine2: [],
    waPostal: [],
    ucNote: [],
    primaryRole: [],
    secondaryRole: [],
    initiative: [],
    technology: [],
    teamName: [],
    qnowUser: [],
    waCity: [],
    haCity: [],
    waState: [],
    haState: [],
    waCountry: [],
    haCountry: [],
    groupMembers: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected userContactService: UserContactService,
    protected qnowUserService: QnowUserService,
    protected cityService: CityService,
    protected stateMasterService: StateMasterService,
    protected countryService: CountryService,
    protected groupMembersService: GroupMembersService,
    protected imEmployeeService: ImEmployeeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ userContact }) => {
      this.updateForm(userContact);
      this.userContact = userContact;
    });
    this.qnowUserService
      .query({ 'userContactId.specified': 'false' })
      .pipe(
        filter((mayBeOk: HttpResponse<IQnowUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IQnowUser[]>) => response.body)
      )
      .subscribe(
        (res: IQnowUser[]) => {
          if (!this.userContact.qnowUser || !this.userContact.qnowUser.id) {
            this.qnowusers = res;
          } else {
            this.qnowUserService
              .find(this.userContact.qnowUser.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IQnowUser>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IQnowUser>) => subResponse.body)
              )
              .subscribe(
                (subRes: IQnowUser) => (this.qnowusers = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.cityService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICity[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICity[]>) => response.body)
      )
      .subscribe((res: ICity[]) => (this.cities = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.stateMasterService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IStateMaster[]>) => mayBeOk.ok),
        map((response: HttpResponse<IStateMaster[]>) => response.body)
      )
      .subscribe((res: IStateMaster[]) => (this.statemasters = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.countryService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICountry[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICountry[]>) => response.body)
      )
      .subscribe((res: ICountry[]) => (this.countries = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.groupMembersService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IGroupMembers[]>) => mayBeOk.ok),
        map((response: HttpResponse<IGroupMembers[]>) => response.body)
      )
      .subscribe((res: IGroupMembers[]) => (this.groupmembers = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.imEmployeeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IImEmployee[]>) => mayBeOk.ok),
        map((response: HttpResponse<IImEmployee[]>) => response.body)
      )
      .subscribe((res: IImEmployee[]) => (this.imemployees = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(userContact: IUserContact) {
    this.editForm.patchValue({
      id: userContact.id,
      homePhone: userContact.homePhone,
      workPhone: userContact.workPhone,
      cellPhone: userContact.cellPhone,
      permentAddress: userContact.permentAddress,
      haLine1: userContact.haLine1,
      haLine2: userContact.haLine2,
      haPostal: userContact.haPostal,
      waLine1: userContact.waLine1,
      waLine2: userContact.waLine2,
      waPostal: userContact.waPostal,
      ucNote: userContact.ucNote,
      primaryRole: userContact.primaryRole,
      secondaryRole: userContact.secondaryRole,
      initiative: userContact.initiative,
      technology: userContact.technology,
      teamName: userContact.teamName,
      qnowUser: userContact.qnowUser,
      waCity: userContact.waCity,
      haCity: userContact.haCity,
      waState: userContact.waState,
      haState: userContact.haState,
      waCountry: userContact.waCountry,
      haCountry: userContact.haCountry,
      groupMembers: userContact.groupMembers
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const userContact = this.createFromForm();
    if (userContact.id !== undefined) {
      this.subscribeToSaveResponse(this.userContactService.update(userContact));
    } else {
      this.subscribeToSaveResponse(this.userContactService.create(userContact));
    }
  }

  private createFromForm(): IUserContact {
    const entity = {
      ...new UserContact(),
      id: this.editForm.get(['id']).value,
      homePhone: this.editForm.get(['homePhone']).value,
      workPhone: this.editForm.get(['workPhone']).value,
      cellPhone: this.editForm.get(['cellPhone']).value,
      permentAddress: this.editForm.get(['permentAddress']).value,
      haLine1: this.editForm.get(['haLine1']).value,
      haLine2: this.editForm.get(['haLine2']).value,
      haPostal: this.editForm.get(['haPostal']).value,
      waLine1: this.editForm.get(['waLine1']).value,
      waLine2: this.editForm.get(['waLine2']).value,
      waPostal: this.editForm.get(['waPostal']).value,
      ucNote: this.editForm.get(['ucNote']).value,
      primaryRole: this.editForm.get(['primaryRole']).value,
      secondaryRole: this.editForm.get(['secondaryRole']).value,
      initiative: this.editForm.get(['initiative']).value,
      technology: this.editForm.get(['technology']).value,
      teamName: this.editForm.get(['teamName']).value,
      qnowUser: this.editForm.get(['qnowUser']).value,
      waCity: this.editForm.get(['waCity']).value,
      haCity: this.editForm.get(['haCity']).value,
      waState: this.editForm.get(['waState']).value,
      haState: this.editForm.get(['haState']).value,
      waCountry: this.editForm.get(['waCountry']).value,
      haCountry: this.editForm.get(['haCountry']).value,
      groupMembers: this.editForm.get(['groupMembers']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserContact>>) {
    result.subscribe((res: HttpResponse<IUserContact>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackQnowUserById(index: number, item: IQnowUser) {
    return item.id;
  }

  trackCityById(index: number, item: ICity) {
    return item.id;
  }

  trackStateMasterById(index: number, item: IStateMaster) {
    return item.id;
  }

  trackCountryById(index: number, item: ICountry) {
    return item.id;
  }

  trackGroupMembersById(index: number, item: IGroupMembers) {
    return item.id;
  }

  trackImEmployeeById(index: number, item: IImEmployee) {
    return item.id;
  }

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
