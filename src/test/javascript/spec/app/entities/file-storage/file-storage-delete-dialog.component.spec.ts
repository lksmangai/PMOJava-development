/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Qnow1TestModule } from '../../../test.module';
import { FileStorageDeleteDialogComponent } from 'app/entities/file-storage/file-storage-delete-dialog.component';
import { FileStorageService } from 'app/entities/file-storage/file-storage.service';

describe('Component Tests', () => {
  describe('FileStorage Management Delete Component', () => {
    let comp: FileStorageDeleteDialogComponent;
    let fixture: ComponentFixture<FileStorageDeleteDialogComponent>;
    let service: FileStorageService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [FileStorageDeleteDialogComponent]
      })
        .overrideTemplate(FileStorageDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FileStorageDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FileStorageService);
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
