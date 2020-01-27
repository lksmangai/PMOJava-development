/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Qnow1TestModule } from '../../../test.module';
import { ImTimesheetComponent } from 'app/entities/im-timesheet/im-timesheet.component';
import { ImTimesheetService } from 'app/entities/im-timesheet/im-timesheet.service';
import { ImTimesheet } from 'app/shared/model/im-timesheet.model';

describe('Component Tests', () => {
  describe('ImTimesheet Management Component', () => {
    let comp: ImTimesheetComponent;
    let fixture: ComponentFixture<ImTimesheetComponent>;
    let service: ImTimesheetService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ImTimesheetComponent],
        providers: []
      })
        .overrideTemplate(ImTimesheetComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ImTimesheetComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ImTimesheetService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ImTimesheet(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.imTimesheets[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
