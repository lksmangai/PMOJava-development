/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Qnow1TestModule } from '../../../test.module';
import { OpportunityPriorityIdComponent } from 'app/entities/opportunity-priority-id/opportunity-priority-id.component';
import { OpportunityPriorityIdService } from 'app/entities/opportunity-priority-id/opportunity-priority-id.service';
import { OpportunityPriorityId } from 'app/shared/model/opportunity-priority-id.model';

describe('Component Tests', () => {
  describe('OpportunityPriorityId Management Component', () => {
    let comp: OpportunityPriorityIdComponent;
    let fixture: ComponentFixture<OpportunityPriorityIdComponent>;
    let service: OpportunityPriorityIdService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [OpportunityPriorityIdComponent],
        providers: []
      })
        .overrideTemplate(OpportunityPriorityIdComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OpportunityPriorityIdComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OpportunityPriorityIdService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new OpportunityPriorityId(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.opportunityPriorityIds[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
