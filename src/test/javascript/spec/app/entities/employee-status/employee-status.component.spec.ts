/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Qnow1TestModule } from '../../../test.module';
import { EmployeeStatusComponent } from 'app/entities/employee-status/employee-status.component';
import { EmployeeStatusService } from 'app/entities/employee-status/employee-status.service';
import { EmployeeStatus } from 'app/shared/model/employee-status.model';

describe('Component Tests', () => {
  describe('EmployeeStatus Management Component', () => {
    let comp: EmployeeStatusComponent;
    let fixture: ComponentFixture<EmployeeStatusComponent>;
    let service: EmployeeStatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [EmployeeStatusComponent],
        providers: []
      })
        .overrideTemplate(EmployeeStatusComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmployeeStatusComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmployeeStatusService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EmployeeStatus(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.employeeStatuses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
