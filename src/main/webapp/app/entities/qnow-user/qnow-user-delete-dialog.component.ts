import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IQnowUser } from 'app/shared/model/qnow-user.model';
import { QnowUserService } from './qnow-user.service';

@Component({
  selector: 'jhi-qnow-user-delete-dialog',
  templateUrl: './qnow-user-delete-dialog.component.html'
})
export class QnowUserDeleteDialogComponent {
  qnowUser: IQnowUser;

  constructor(protected qnowUserService: QnowUserService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.qnowUserService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'qnowUserListModification',
        content: 'Deleted an qnowUser'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-qnow-user-delete-popup',
  template: ''
})
export class QnowUserDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ qnowUser }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(QnowUserDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.qnowUser = qnowUser;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/qnow-user', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/qnow-user', { outlets: { popup: null } }]);
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
