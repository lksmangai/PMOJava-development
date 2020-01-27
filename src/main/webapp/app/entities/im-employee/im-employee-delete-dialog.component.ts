import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IImEmployee } from 'app/shared/model/im-employee.model';
import { ImEmployeeService } from './im-employee.service';

@Component({
  selector: 'jhi-im-employee-delete-dialog',
  templateUrl: './im-employee-delete-dialog.component.html'
})
export class ImEmployeeDeleteDialogComponent {
  imEmployee: IImEmployee;

  constructor(
    protected imEmployeeService: ImEmployeeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.imEmployeeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'imEmployeeListModification',
        content: 'Deleted an imEmployee'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-im-employee-delete-popup',
  template: ''
})
export class ImEmployeeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ imEmployee }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ImEmployeeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.imEmployee = imEmployee;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/im-employee', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/im-employee', { outlets: { popup: null } }]);
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
