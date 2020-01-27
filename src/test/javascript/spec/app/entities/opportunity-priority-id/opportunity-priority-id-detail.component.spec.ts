/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { OpportunityPriorityIdDetailComponent } from 'app/entities/opportunity-priority-id/opportunity-priority-id-detail.component';
import { OpportunityPriorityId } from 'app/shared/model/opportunity-priority-id.model';

describe('Component Tests', () => {
  describe('OpportunityPriorityId Management Detail Component', () => {
    let comp: OpportunityPriorityIdDetailComponent;
    let fixture: ComponentFixture<OpportunityPriorityIdDetailComponent>;
    const route = ({ data: of({ opportunityPriorityId: new OpportunityPriorityId(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [OpportunityPriorityIdDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OpportunityPriorityIdDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OpportunityPriorityIdDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.opportunityPriorityId).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
