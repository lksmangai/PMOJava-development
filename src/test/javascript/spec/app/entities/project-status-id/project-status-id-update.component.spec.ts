/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectStatusIdUpdateComponent } from 'app/entities/project-status-id/project-status-id-update.component';
import { ProjectStatusIdService } from 'app/entities/project-status-id/project-status-id.service';
import { ProjectStatusId } from 'app/shared/model/project-status-id.model';

describe('Component Tests', () => {
  describe('ProjectStatusId Management Update Component', () => {
    let comp: ProjectStatusIdUpdateComponent;
    let fixture: ComponentFixture<ProjectStatusIdUpdateComponent>;
    let service: ProjectStatusIdService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectStatusIdUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProjectStatusIdUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProjectStatusIdUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectStatusIdService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProjectStatusId(123);
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
        const entity = new ProjectStatusId();
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
