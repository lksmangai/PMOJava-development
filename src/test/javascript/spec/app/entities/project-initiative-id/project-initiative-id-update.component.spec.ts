/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectInitiativeIdUpdateComponent } from 'app/entities/project-initiative-id/project-initiative-id-update.component';
import { ProjectInitiativeIdService } from 'app/entities/project-initiative-id/project-initiative-id.service';
import { ProjectInitiativeId } from 'app/shared/model/project-initiative-id.model';

describe('Component Tests', () => {
  describe('ProjectInitiativeId Management Update Component', () => {
    let comp: ProjectInitiativeIdUpdateComponent;
    let fixture: ComponentFixture<ProjectInitiativeIdUpdateComponent>;
    let service: ProjectInitiativeIdService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectInitiativeIdUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProjectInitiativeIdUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProjectInitiativeIdUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectInitiativeIdService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProjectInitiativeId(123);
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
        const entity = new ProjectInitiativeId();
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
