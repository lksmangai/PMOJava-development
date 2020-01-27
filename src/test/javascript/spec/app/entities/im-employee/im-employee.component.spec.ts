/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Qnow1TestModule } from '../../../test.module';
import { ImEmployeeComponent } from 'app/entities/im-employee/im-employee.component';
import { ImEmployeeService } from 'app/entities/im-employee/im-employee.service';
import { ImEmployee } from 'app/shared/model/im-employee.model';

describe('Component Tests', () => {
  describe('ImEmployee Management Component', () => {
    let comp: ImEmployeeComponent;
    let fixture: ComponentFixture<ImEmployeeComponent>;
    let service: ImEmployeeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ImEmployeeComponent],
        providers: []
      })
        .overrideTemplate(ImEmployeeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ImEmployeeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ImEmployeeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ImEmployee(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.imEmployees[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
