/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { SkillExpertiseUpdateComponent } from 'app/entities/skill-expertise/skill-expertise-update.component';
import { SkillExpertiseService } from 'app/entities/skill-expertise/skill-expertise.service';
import { SkillExpertise } from 'app/shared/model/skill-expertise.model';

describe('Component Tests', () => {
  describe('SkillExpertise Management Update Component', () => {
    let comp: SkillExpertiseUpdateComponent;
    let fixture: ComponentFixture<SkillExpertiseUpdateComponent>;
    let service: SkillExpertiseService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [SkillExpertiseUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SkillExpertiseUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SkillExpertiseUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SkillExpertiseService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SkillExpertise(123);
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
        const entity = new SkillExpertise();
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
