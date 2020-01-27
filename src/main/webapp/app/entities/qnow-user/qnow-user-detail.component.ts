import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQnowUser } from 'app/shared/model/qnow-user.model';

@Component({
  selector: 'jhi-qnow-user-detail',
  templateUrl: './qnow-user-detail.component.html'
})
export class QnowUserDetailComponent implements OnInit {
  qnowUser: IQnowUser;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ qnowUser }) => {
      this.qnowUser = qnowUser;
    });
  }

  previousState() {
    window.history.back();
  }
}
