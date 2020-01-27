/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectCostCenterIdUpdateComponent } from 'app/entities/project-cost-center-id/project-cost-center-id-update.component';
import { ProjectCostCenterIdService } from 'app/entities/project-cost-center-id/project-cost-center-id.service';
import { ProjectCostCenterId } from 'app/shared/model/project-cost-center-id.model';

describe('Component Tests', () => {
  describe('ProjectCostCenterId Management Update Component', () => {
    let comp: ProjectCostCenterIdUpdateComponent;
    let fixture: ComponentFixture<ProjectCostCenterIdUpdateComponent>;
    let service: ProjectCostCenterIdService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectCostCenterIdUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProjectCostCenterIdUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProjectCostCenterIdUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectCostCenterIdService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProjectCostCenterId(123);
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
        const entity = new ProjectCostCenterId();
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
