/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Qnow1TestModule } from '../../../test.module';
import { BacklogPracticeComponent } from 'app/entities/backlog-practice/backlog-practice.component';
import { BacklogPracticeService } from 'app/entities/backlog-practice/backlog-practice.service';
import { BacklogPractice } from 'app/shared/model/backlog-practice.model';

describe('Component Tests', () => {
  describe('BacklogPractice Management Component', () => {
    let comp: BacklogPracticeComponent;
    let fixture: ComponentFixture<BacklogPracticeComponent>;
    let service: BacklogPracticeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [BacklogPracticeComponent],
        providers: []
      })
        .overrideTemplate(BacklogPracticeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BacklogPracticeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BacklogPracticeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new BacklogPractice(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.backlogPractices[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
