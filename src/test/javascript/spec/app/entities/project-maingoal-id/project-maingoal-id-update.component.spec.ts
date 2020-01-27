/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectMaingoalIdUpdateComponent } from 'app/entities/project-maingoal-id/project-maingoal-id-update.component';
import { ProjectMaingoalIdService } from 'app/entities/project-maingoal-id/project-maingoal-id.service';
import { ProjectMaingoalId } from 'app/shared/model/project-maingoal-id.model';

describe('Component Tests', () => {
  describe('ProjectMaingoalId Management Update Component', () => {
    let comp: ProjectMaingoalIdUpdateComponent;
    let fixture: ComponentFixture<ProjectMaingoalIdUpdateComponent>;
    let service: ProjectMaingoalIdService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectMaingoalIdUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProjectMaingoalIdUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProjectMaingoalIdUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectMaingoalIdService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProjectMaingoalId(123);
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
        const entity = new ProjectMaingoalId();
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
