import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IImTimesheet } from 'app/shared/model/im-timesheet.model';
import { ImTimesheetService } from './im-timesheet.service';

@Component({
  selector: 'jhi-im-timesheet-delete-dialog',
  templateUrl: './im-timesheet-delete-dialog.component.html'
})
export class ImTimesheetDeleteDialogComponent {
  imTimesheet: IImTimesheet;

  constructor(
    protected imTimesheetService: ImTimesheetService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.imTimesheetService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'imTimesheetListModification',
        content: 'Deleted an imTimesheet'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-im-timesheet-delete-popup',
  template: ''
})
export class ImTimesheetDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ imTimesheet }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ImTimesheetDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.imTimesheet = imTimesheet;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/im-timesheet', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/im-timesheet', { outlets: { popup: null } }]);
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
