/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Qnow1TestModule } from '../../../test.module';
import { QnowUserDeleteDialogComponent } from 'app/entities/qnow-user/qnow-user-delete-dialog.component';
import { QnowUserService } from 'app/entities/qnow-user/qnow-user.service';

describe('Component Tests', () => {
  describe('QnowUser Management Delete Component', () => {
    let comp: QnowUserDeleteDialogComponent;
    let fixture: ComponentFixture<QnowUserDeleteDialogComponent>;
    let service: QnowUserService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [QnowUserDeleteDialogComponent]
      })
        .overrideTemplate(QnowUserDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(QnowUserDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(QnowUserService);
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
