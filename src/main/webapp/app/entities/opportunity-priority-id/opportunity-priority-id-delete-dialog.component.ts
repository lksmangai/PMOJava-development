import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOpportunityPriorityId } from 'app/shared/model/opportunity-priority-id.model';
import { OpportunityPriorityIdService } from './opportunity-priority-id.service';

@Component({
  selector: 'jhi-opportunity-priority-id-delete-dialog',
  templateUrl: './opportunity-priority-id-delete-dialog.component.html'
})
export class OpportunityPriorityIdDeleteDialogComponent {
  opportunityPriorityId: IOpportunityPriorityId;

  constructor(
    protected opportunityPriorityIdService: OpportunityPriorityIdService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.opportunityPriorityIdService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'opportunityPriorityIdListModification',
        content: 'Deleted an opportunityPriorityId'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-opportunity-priority-id-delete-popup',
  template: ''
})
export class OpportunityPriorityIdDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ opportunityPriorityId }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(OpportunityPriorityIdDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.opportunityPriorityId = opportunityPriorityId;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/opportunity-priority-id', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/opportunity-priority-id', { outlets: { popup: null } }]);
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
