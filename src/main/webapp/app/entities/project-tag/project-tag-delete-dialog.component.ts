import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProjectTag } from 'app/shared/model/project-tag.model';
import { ProjectTagService } from './project-tag.service';

@Component({
  selector: 'jhi-project-tag-delete-dialog',
  templateUrl: './project-tag-delete-dialog.component.html'
})
export class ProjectTagDeleteDialogComponent {
  projectTag: IProjectTag;

  constructor(
    protected projectTagService: ProjectTagService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.projectTagService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'projectTagListModification',
        content: 'Deleted an projectTag'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-project-tag-delete-popup',
  template: ''
})
export class ProjectTagDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ projectTag }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProjectTagDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.projectTag = projectTag;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/project-tag', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/project-tag', { outlets: { popup: null } }]);
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
