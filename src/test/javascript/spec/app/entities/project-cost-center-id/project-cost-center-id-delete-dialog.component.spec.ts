/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectCostCenterIdDeleteDialogComponent } from 'app/entities/project-cost-center-id/project-cost-center-id-delete-dialog.component';
import { ProjectCostCenterIdService } from 'app/entities/project-cost-center-id/project-cost-center-id.service';

describe('Component Tests', () => {
  describe('ProjectCostCenterId Management Delete Component', () => {
    let comp: ProjectCostCenterIdDeleteDialogComponent;
    let fixture: ComponentFixture<ProjectCostCenterIdDeleteDialogComponent>;
    let service: ProjectCostCenterIdService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectCostCenterIdDeleteDialogComponent]
      })
        .overrideTemplate(ProjectCostCenterIdDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProjectCostCenterIdDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectCostCenterIdService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
