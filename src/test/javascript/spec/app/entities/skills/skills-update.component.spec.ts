/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { SkillsUpdateComponent } from 'app/entities/skills/skills-update.component';
import { SkillsService } from 'app/entities/skills/skills.service';
import { Skills } from 'app/shared/model/skills.model';

describe('Component Tests', () => {
  describe('Skills Management Update Component', () => {
    let comp: SkillsUpdateComponent;
    let fixture: ComponentFixture<SkillsUpdateComponent>;
    let service: SkillsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [SkillsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SkillsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SkillsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SkillsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Skills(123);
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
        const entity = new Skills();
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
