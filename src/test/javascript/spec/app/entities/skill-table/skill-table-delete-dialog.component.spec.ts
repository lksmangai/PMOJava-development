/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Qnow1TestModule } from '../../../test.module';
import { SkillTableDeleteDialogComponent } from 'app/entities/skill-table/skill-table-delete-dialog.component';
import { SkillTableService } from 'app/entities/skill-table/skill-table.service';

describe('Component Tests', () => {
  describe('SkillTable Management Delete Component', () => {
    let comp: SkillTableDeleteDialogComponent;
    let fixture: ComponentFixture<SkillTableDeleteDialogComponent>;
    let service: SkillTableService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [SkillTableDeleteDialogComponent]
      })
        .overrideTemplate(SkillTableDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SkillTableDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SkillTableService);
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
