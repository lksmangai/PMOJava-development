/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectTagDeleteDialogComponent } from 'app/entities/project-tag/project-tag-delete-dialog.component';
import { ProjectTagService } from 'app/entities/project-tag/project-tag.service';

describe('Component Tests', () => {
  describe('ProjectTag Management Delete Component', () => {
    let comp: ProjectTagDeleteDialogComponent;
    let fixture: ComponentFixture<ProjectTagDeleteDialogComponent>;
    let service: ProjectTagService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectTagDeleteDialogComponent]
      })
        .overrideTemplate(ProjectTagDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProjectTagDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectTagService);
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
