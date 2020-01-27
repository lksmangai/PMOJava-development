import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IImTimesheet } from 'app/shared/model/im-timesheet.model';

@Component({
  selector: 'jhi-im-timesheet-detail',
  templateUrl: './im-timesheet-detail.component.html'
})
export class ImTimesheetDetailComponent implements OnInit {
  imTimesheet: IImTimesheet;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ imTimesheet }) => {
      this.imTimesheet = imTimesheet;
    });
  }

  previousState() {
    window.history.back();
  }
}
