/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectBucketIdDeleteDialogComponent } from 'app/entities/project-bucket-id/project-bucket-id-delete-dialog.component';
import { ProjectBucketIdService } from 'app/entities/project-bucket-id/project-bucket-id.service';

describe('Component Tests', () => {
  describe('ProjectBucketId Management Delete Component', () => {
    let comp: ProjectBucketIdDeleteDialogComponent;
    let fixture: ComponentFixture<ProjectBucketIdDeleteDialogComponent>;
    let service: ProjectBucketIdService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectBucketIdDeleteDialogComponent]
      })
        .overrideTemplate(ProjectBucketIdDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProjectBucketIdDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectBucketIdService);
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
