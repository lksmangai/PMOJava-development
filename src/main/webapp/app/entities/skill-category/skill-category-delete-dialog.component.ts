import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISkillCategory } from 'app/shared/model/skill-category.model';
import { SkillCategoryService } from './skill-category.service';

@Component({
  selector: 'jhi-skill-category-delete-dialog',
  templateUrl: './skill-category-delete-dialog.component.html'
})
export class SkillCategoryDeleteDialogComponent {
  skillCategory: ISkillCategory;

  constructor(
    protected skillCategoryService: SkillCategoryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.skillCategoryService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'skillCategoryListModification',
        content: 'Deleted an skillCategory'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-skill-category-delete-popup',
  template: ''
})
export class SkillCategoryDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ skillCategory }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SkillCategoryDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.skillCategory = skillCategory;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/skill-category', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/skill-category', { outlets: { popup: null } }]);
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
