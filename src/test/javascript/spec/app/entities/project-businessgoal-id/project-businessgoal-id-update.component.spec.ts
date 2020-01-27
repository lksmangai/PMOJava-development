/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectBusinessgoalIdUpdateComponent } from 'app/entities/project-businessgoal-id/project-businessgoal-id-update.component';
import { ProjectBusinessgoalIdService } from 'app/entities/project-businessgoal-id/project-businessgoal-id.service';
import { ProjectBusinessgoalId } from 'app/shared/model/project-businessgoal-id.model';

describe('Component Tests', () => {
  describe('ProjectBusinessgoalId Management Update Component', () => {
    let comp: ProjectBusinessgoalIdUpdateComponent;
    let fixture: ComponentFixture<ProjectBusinessgoalIdUpdateComponent>;
    let service: ProjectBusinessgoalIdService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectBusinessgoalIdUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProjectBusinessgoalIdUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProjectBusinessgoalIdUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectBusinessgoalIdService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProjectBusinessgoalId(123);
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
        const entity = new ProjectBusinessgoalId();
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
