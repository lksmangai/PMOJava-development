/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Qnow1TestModule } from '../../../test.module';
import { UserContactDeleteDialogComponent } from 'app/entities/user-contact/user-contact-delete-dialog.component';
import { UserContactService } from 'app/entities/user-contact/user-contact.service';

describe('Component Tests', () => {
  describe('UserContact Management Delete Component', () => {
    let comp: UserContactDeleteDialogComponent;
    let fixture: ComponentFixture<UserContactDeleteDialogComponent>;
    let service: UserContactService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [UserContactDeleteDialogComponent]
      })
        .overrideTemplate(UserContactDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserContactDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserContactService);
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
