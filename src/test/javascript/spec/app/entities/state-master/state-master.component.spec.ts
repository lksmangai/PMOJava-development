/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Qnow1TestModule } from '../../../test.module';
import { StateMasterComponent } from 'app/entities/state-master/state-master.component';
import { StateMasterService } from 'app/entities/state-master/state-master.service';
import { StateMaster } from 'app/shared/model/state-master.model';

describe('Component Tests', () => {
  describe('StateMaster Management Component', () => {
    let comp: StateMasterComponent;
    let fixture: ComponentFixture<StateMasterComponent>;
    let service: StateMasterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [StateMasterComponent],
        providers: []
      })
        .overrideTemplate(StateMasterComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StateMasterComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StateMasterService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new StateMaster(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.stateMasters[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
