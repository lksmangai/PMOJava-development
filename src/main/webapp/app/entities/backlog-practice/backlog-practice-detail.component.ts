import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBacklogPractice } from 'app/shared/model/backlog-practice.model';

@Component({
  selector: 'jhi-backlog-practice-detail',
  templateUrl: './backlog-practice-detail.component.html'
})
export class BacklogPracticeDetailComponent implements OnInit {
  backlogPractice: IBacklogPractice;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ backlogPractice }) => {
      this.backlogPractice = backlogPractice;
    });
  }

  previousState() {
    window.history.back();
  }
}
