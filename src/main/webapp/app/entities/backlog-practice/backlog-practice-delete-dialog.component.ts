import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBacklogPractice } from 'app/shared/model/backlog-practice.model';
import { BacklogPracticeService } from './backlog-practice.service';

@Component({
  selector: 'jhi-backlog-practice-delete-dialog',
  templateUrl: './backlog-practice-delete-dialog.component.html'
})
export class BacklogPracticeDeleteDialogComponent {
  backlogPractice: IBacklogPractice;

  constructor(
    protected backlogPracticeService: BacklogPracticeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.backlogPracticeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'backlogPracticeListModification',
        content: 'Deleted an backlogPractice'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-backlog-practice-delete-popup',
  template: ''
})
export class BacklogPracticeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ backlogPractice }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(BacklogPracticeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.backlogPractice = backlogPractice;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/backlog-practice', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/backlog-practice', { outlets: { popup: null } }]);
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
