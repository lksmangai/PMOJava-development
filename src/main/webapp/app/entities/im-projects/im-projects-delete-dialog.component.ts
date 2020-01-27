import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IImProjects } from 'app/shared/model/im-projects.model';
import { ImProjectsService } from './im-projects.service';

@Component({
  selector: 'jhi-im-projects-delete-dialog',
  templateUrl: './im-projects-delete-dialog.component.html'
})
export class ImProjectsDeleteDialogComponent {
  imProjects: IImProjects;

  constructor(
    protected imProjectsService: ImProjectsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.imProjectsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'imProjectsListModification',
        content: 'Deleted an imProjects'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-im-projects-delete-popup',
  template: ''
})
export class ImProjectsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ imProjects }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ImProjectsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.imProjects = imProjects;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/im-projects', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/im-projects', { outlets: { popup: null } }]);
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
