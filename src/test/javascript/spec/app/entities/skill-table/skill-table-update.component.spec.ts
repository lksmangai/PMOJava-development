/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { SkillTableUpdateComponent } from 'app/entities/skill-table/skill-table-update.component';
import { SkillTableService } from 'app/entities/skill-table/skill-table.service';
import { SkillTable } from 'app/shared/model/skill-table.model';

describe('Component Tests', () => {
  describe('SkillTable Management Update Component', () => {
    let comp: SkillTableUpdateComponent;
    let fixture: ComponentFixture<SkillTableUpdateComponent>;
    let service: SkillTableService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [SkillTableUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SkillTableUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SkillTableUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SkillTableService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SkillTable(123);
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
        const entity = new SkillTable();
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
