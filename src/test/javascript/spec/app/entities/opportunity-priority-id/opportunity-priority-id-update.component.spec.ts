/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { OpportunityPriorityIdUpdateComponent } from 'app/entities/opportunity-priority-id/opportunity-priority-id-update.component';
import { OpportunityPriorityIdService } from 'app/entities/opportunity-priority-id/opportunity-priority-id.service';
import { OpportunityPriorityId } from 'app/shared/model/opportunity-priority-id.model';

describe('Component Tests', () => {
  describe('OpportunityPriorityId Management Update Component', () => {
    let comp: OpportunityPriorityIdUpdateComponent;
    let fixture: ComponentFixture<OpportunityPriorityIdUpdateComponent>;
    let service: OpportunityPriorityIdService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [OpportunityPriorityIdUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(OpportunityPriorityIdUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OpportunityPriorityIdUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OpportunityPriorityIdService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new OpportunityPriorityId(123);
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
        const entity = new OpportunityPriorityId();
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
