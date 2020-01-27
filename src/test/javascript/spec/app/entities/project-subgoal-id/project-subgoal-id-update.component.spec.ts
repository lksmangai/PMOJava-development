/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectSubgoalIdUpdateComponent } from 'app/entities/project-subgoal-id/project-subgoal-id-update.component';
import { ProjectSubgoalIdService } from 'app/entities/project-subgoal-id/project-subgoal-id.service';
import { ProjectSubgoalId } from 'app/shared/model/project-subgoal-id.model';

describe('Component Tests', () => {
  describe('ProjectSubgoalId Management Update Component', () => {
    let comp: ProjectSubgoalIdUpdateComponent;
    let fixture: ComponentFixture<ProjectSubgoalIdUpdateComponent>;
    let service: ProjectSubgoalIdService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectSubgoalIdUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProjectSubgoalIdUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProjectSubgoalIdUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectSubgoalIdService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProjectSubgoalId(123);
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
        const entity = new ProjectSubgoalId();
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
