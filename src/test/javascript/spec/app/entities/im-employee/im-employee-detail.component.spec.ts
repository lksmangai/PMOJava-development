/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { ImEmployeeDetailComponent } from 'app/entities/im-employee/im-employee-detail.component';
import { ImEmployee } from 'app/shared/model/im-employee.model';

describe('Component Tests', () => {
  describe('ImEmployee Management Detail Component', () => {
    let comp: ImEmployeeDetailComponent;
    let fixture: ComponentFixture<ImEmployeeDetailComponent>;
    const route = ({ data: of({ imEmployee: new ImEmployee(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ImEmployeeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ImEmployeeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ImEmployeeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.imEmployee).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
