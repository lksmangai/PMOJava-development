import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmployeeStatus } from 'app/shared/model/employee-status.model';
import { EmployeeStatusService } from './employee-status.service';

@Component({
  selector: 'jhi-employee-status-delete-dialog',
  templateUrl: './employee-status-delete-dialog.component.html'
})
export class EmployeeStatusDeleteDialogComponent {
  employeeStatus: IEmployeeStatus;

  constructor(
    protected employeeStatusService: EmployeeStatusService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.employeeStatusService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'employeeStatusListModification',
        content: 'Deleted an employeeStatus'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-employee-status-delete-popup',
  template: ''
})
export class EmployeeStatusDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ employeeStatus }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(EmployeeStatusDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.employeeStatus = employeeStatus;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/employee-status', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/employee-status', { outlets: { popup: null } }]);
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
