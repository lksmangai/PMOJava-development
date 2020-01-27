/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectVerticalDeleteDialogComponent } from 'app/entities/project-vertical/project-vertical-delete-dialog.component';
import { ProjectVerticalService } from 'app/entities/project-vertical/project-vertical.service';

describe('Component Tests', () => {
  describe('ProjectVertical Management Delete Component', () => {
    let comp: ProjectVerticalDeleteDialogComponent;
    let fixture: ComponentFixture<ProjectVerticalDeleteDialogComponent>;
    let service: ProjectVerticalService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectVerticalDeleteDialogComponent]
      })
        .overrideTemplate(ProjectVerticalDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProjectVerticalDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectVerticalService);
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
