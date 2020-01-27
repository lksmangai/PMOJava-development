/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectMaingoalIdDeleteDialogComponent } from 'app/entities/project-maingoal-id/project-maingoal-id-delete-dialog.component';
import { ProjectMaingoalIdService } from 'app/entities/project-maingoal-id/project-maingoal-id.service';

describe('Component Tests', () => {
  describe('ProjectMaingoalId Management Delete Component', () => {
    let comp: ProjectMaingoalIdDeleteDialogComponent;
    let fixture: ComponentFixture<ProjectMaingoalIdDeleteDialogComponent>;
    let service: ProjectMaingoalIdService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectMaingoalIdDeleteDialogComponent]
      })
        .overrideTemplate(ProjectMaingoalIdDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProjectMaingoalIdDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectMaingoalIdService);
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
