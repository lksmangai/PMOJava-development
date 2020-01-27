/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectThemeDeleteDialogComponent } from 'app/entities/project-theme/project-theme-delete-dialog.component';
import { ProjectThemeService } from 'app/entities/project-theme/project-theme.service';

describe('Component Tests', () => {
  describe('ProjectTheme Management Delete Component', () => {
    let comp: ProjectThemeDeleteDialogComponent;
    let fixture: ComponentFixture<ProjectThemeDeleteDialogComponent>;
    let service: ProjectThemeService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectThemeDeleteDialogComponent]
      })
        .overrideTemplate(ProjectThemeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProjectThemeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectThemeService);
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
