import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITagType } from 'app/shared/model/tag-type.model';
import { TagTypeService } from './tag-type.service';

@Component({
  selector: 'jhi-tag-type-delete-dialog',
  templateUrl: './tag-type-delete-dialog.component.html'
})
export class TagTypeDeleteDialogComponent {
  tagType: ITagType;

  constructor(protected tagTypeService: TagTypeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.tagTypeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'tagTypeListModification',
        content: 'Deleted an tagType'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-tag-type-delete-popup',
  template: ''
})
export class TagTypeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tagType }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TagTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.tagType = tagType;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/tag-type', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/tag-type', { outlets: { popup: null } }]);
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
