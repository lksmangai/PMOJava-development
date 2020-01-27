/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { ImTimesheetDetailComponent } from 'app/entities/im-timesheet/im-timesheet-detail.component';
import { ImTimesheet } from 'app/shared/model/im-timesheet.model';

describe('Component Tests', () => {
  describe('ImTimesheet Management Detail Component', () => {
    let comp: ImTimesheetDetailComponent;
    let fixture: ComponentFixture<ImTimesheetDetailComponent>;
    const route = ({ data: of({ imTimesheet: new ImTimesheet(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ImTimesheetDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ImTimesheetDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ImTimesheetDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.imTimesheet).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
