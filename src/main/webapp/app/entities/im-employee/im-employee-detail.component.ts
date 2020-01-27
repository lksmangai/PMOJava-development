import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IImEmployee } from 'app/shared/model/im-employee.model';

@Component({
  selector: 'jhi-im-employee-detail',
  templateUrl: './im-employee-detail.component.html'
})
export class ImEmployeeDetailComponent implements OnInit {
  imEmployee: IImEmployee;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ imEmployee }) => {
      this.imEmployee = imEmployee;
    });
  }

  previousState() {
    window.history.back();
  }
}
