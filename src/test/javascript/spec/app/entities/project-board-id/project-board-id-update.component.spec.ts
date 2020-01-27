/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectBoardIdUpdateComponent } from 'app/entities/project-board-id/project-board-id-update.component';
import { ProjectBoardIdService } from 'app/entities/project-board-id/project-board-id.service';
import { ProjectBoardId } from 'app/shared/model/project-board-id.model';

describe('Component Tests', () => {
  describe('ProjectBoardId Management Update Component', () => {
    let comp: ProjectBoardIdUpdateComponent;
    let fixture: ComponentFixture<ProjectBoardIdUpdateComponent>;
    let service: ProjectBoardIdService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectBoardIdUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProjectBoardIdUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProjectBoardIdUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectBoardIdService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProjectBoardId(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProjectBoardId();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
