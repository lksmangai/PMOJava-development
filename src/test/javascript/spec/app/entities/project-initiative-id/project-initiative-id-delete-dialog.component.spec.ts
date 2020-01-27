/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectInitiativeIdDeleteDialogComponent } from 'app/entities/project-initiative-id/project-initiative-id-delete-dialog.component';
import { ProjectInitiativeIdService } from 'app/entities/project-initiative-id/project-initiative-id.service';

describe('Component Tests', () => {
  describe('ProjectInitiativeId Management Delete Component', () => {
    let comp: ProjectInitiativeIdDeleteDialogComponent;
    let fixture: ComponentFixture<ProjectInitiativeIdDeleteDialogComponent>;
    let service: ProjectInitiativeIdService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectInitiativeIdDeleteDialogComponent]
      })
        .overrideTemplate(ProjectInitiativeIdDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProjectInitiativeIdDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectInitiativeIdService);
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
