/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { EmployeeStatusDetailComponent } from 'app/entities/employee-status/employee-status-detail.component';
import { EmployeeStatus } from 'app/shared/model/employee-status.model';

describe('Component Tests', () => {
  describe('EmployeeStatus Management Detail Component', () => {
    let comp: EmployeeStatusDetailComponent;
    let fixture: ComponentFixture<EmployeeStatusDetailComponent>;
    const route = ({ data: of({ employeeStatus: new EmployeeStatus(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [EmployeeStatusDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EmployeeStatusDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmployeeStatusDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.employeeStatus).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
