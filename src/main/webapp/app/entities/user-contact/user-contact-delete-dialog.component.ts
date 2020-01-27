import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserContact } from 'app/shared/model/user-contact.model';
import { UserContactService } from './user-contact.service';

@Component({
  selector: 'jhi-user-contact-delete-dialog',
  templateUrl: './user-contact-delete-dialog.component.html'
})
export class UserContactDeleteDialogComponent {
  userContact: IUserContact;

  constructor(
    protected userContactService: UserContactService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.userContactService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'userContactListModification',
        content: 'Deleted an userContact'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-user-contact-delete-popup',
  template: ''
})
export class UserContactDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ userContact }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(UserContactDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.userContact = userContact;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/user-contact', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/user-contact', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
