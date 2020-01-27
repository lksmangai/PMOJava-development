import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISkillExpertise } from 'app/shared/model/skill-expertise.model';
import { SkillExpertiseService } from './skill-expertise.service';

@Component({
  selector: 'jhi-skill-expertise-delete-dialog',
  templateUrl: './skill-expertise-delete-dialog.component.html'
})
export class SkillExpertiseDeleteDialogComponent {
  skillExpertise: ISkillExpertise;

  constructor(
    protected skillExpertiseService: SkillExpertiseService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.skillExpertiseService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'skillExpertiseListModification',
        content: 'Deleted an skillExpertise'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-skill-expertise-delete-popup',
  template: ''
})
export class SkillExpertiseDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ skillExpertise }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SkillExpertiseDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.skillExpertise = skillExpertise;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/skill-expertise', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/skill-expertise', { outlets: { popup: null } }]);
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
