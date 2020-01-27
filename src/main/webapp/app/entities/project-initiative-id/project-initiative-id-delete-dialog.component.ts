import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProjectInitiativeId } from 'app/shared/model/project-initiative-id.model';
import { ProjectInitiativeIdService } from './project-initiative-id.service';

@Component({
  selector: 'jhi-project-initiative-id-delete-dialog',
  templateUrl: './project-initiative-id-delete-dialog.component.html'
})
export class ProjectInitiativeIdDeleteDialogComponent {
  projectInitiativeId: IProjectInitiativeId;

  constructor(
    protected projectInitiativeIdService: ProjectInitiativeIdService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.projectInitiativeIdService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'projectInitiativeIdListModification',
        content: 'Deleted an projectInitiativeId'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-project-initiative-id-delete-popup',
  template: ''
})
export class ProjectInitiativeIdDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ projectInitiativeId }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProjectInitiativeIdDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.projectInitiativeId = projectInitiativeId;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/project-initiative-id', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/project-initiative-id', { outlets: { popup: null } }]);
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
