/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectTypeIdDeleteDialogComponent } from 'app/entities/project-type-id/project-type-id-delete-dialog.component';
import { ProjectTypeIdService } from 'app/entities/project-type-id/project-type-id.service';

describe('Component Tests', () => {
  describe('ProjectTypeId Management Delete Component', () => {
    let comp: ProjectTypeIdDeleteDialogComponent;
    let fixture: ComponentFixture<ProjectTypeIdDeleteDialogComponent>;
    let service: ProjectTypeIdService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectTypeIdDeleteDialogComponent]
      })
        .overrideTemplate(ProjectTypeIdDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProjectTypeIdDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectTypeIdService);
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
