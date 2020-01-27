import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ICity, City } from 'app/shared/model/city.model';
import { CityService } from './city.service';

@Component({
  selector: 'jhi-city-update',
  templateUrl: './city-update.component.html'
})
export class CityUpdateComponent implements OnInit {
  city: ICity;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    cityCode: [],
    cityName: []
  });

  constructor(protected cityService: CityService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ city }) => {
      this.updateForm(city);
      this.city = city;
    });
  }

  updateForm(city: ICity) {
    this.editForm.patchValue({
      id: city.id,
      cityCode: city.cityCode,
      cityName: city.cityName
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const city = this.createFromForm();
    if (city.id !== undefined) {
      this.subscribeToSaveResponse(this.cityService.update(city));
    } else {
      this.subscribeToSaveResponse(this.cityService.create(city));
    }
  }

  private createFromForm(): ICity {
    const entity = {
      ...new City(),
      id: this.editForm.get(['id']).value,
      cityCode: this.editForm.get(['cityCode']).value,
      cityName: this.editForm.get(['cityName']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICity>>) {
    result.subscribe((res: HttpResponse<ICity>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
