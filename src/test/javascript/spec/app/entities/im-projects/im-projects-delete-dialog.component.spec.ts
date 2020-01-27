/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Qnow1TestModule } from '../../../test.module';
import { ImProjectsDeleteDialogComponent } from 'app/entities/im-projects/im-projects-delete-dialog.component';
import { ImProjectsService } from 'app/entities/im-projects/im-projects.service';

describe('Component Tests', () => {
  describe('ImProjects Management Delete Component', () => {
    let comp: ImProjectsDeleteDialogComponent;
    let fixture: ComponentFixture<ImProjectsDeleteDialogComponent>;
    let service: ImProjectsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ImProjectsDeleteDialogComponent]
      })
        .overrideTemplate(ImProjectsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ImProjectsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ImProjectsService);
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
