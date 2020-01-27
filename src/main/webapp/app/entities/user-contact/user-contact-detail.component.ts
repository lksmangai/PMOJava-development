import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserContact } from 'app/shared/model/user-contact.model';

@Component({
  selector: 'jhi-user-contact-detail',
  templateUrl: './user-contact-detail.component.html'
})
export class UserContactDetailComponent implements OnInit {
  userContact: IUserContact;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ userContact }) => {
      this.userContact = userContact;
    });
  }

  previousState() {
    window.history.back();
  }
}
