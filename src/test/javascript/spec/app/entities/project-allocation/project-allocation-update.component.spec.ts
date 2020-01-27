/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectAllocationUpdateComponent } from 'app/entities/project-allocation/project-allocation-update.component';
import { ProjectAllocationService } from 'app/entities/project-allocation/project-allocation.service';
import { ProjectAllocation } from 'app/shared/model/project-allocation.model';

describe('Component Tests', () => {
  describe('ProjectAllocation Management Update Component', () => {
    let comp: ProjectAllocationUpdateComponent;
    let fixture: ComponentFixture<ProjectAllocationUpdateComponent>;
    let service: ProjectAllocationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectAllocationUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProjectAllocationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProjectAllocationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectAllocationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProjectAllocation(123);
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
        const entity = new ProjectAllocation();
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
