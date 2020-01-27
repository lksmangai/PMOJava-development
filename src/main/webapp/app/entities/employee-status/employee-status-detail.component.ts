import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmployeeStatus } from 'app/shared/model/employee-status.model';

@Component({
  selector: 'jhi-employee-status-detail',
  templateUrl: './employee-status-detail.component.html'
})
export class EmployeeStatusDetailComponent implements OnInit {
  employeeStatus: IEmployeeStatus;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ employeeStatus }) => {
      this.employeeStatus = employeeStatus;
    });
  }

  previousState() {
    window.history.back();
  }
}
