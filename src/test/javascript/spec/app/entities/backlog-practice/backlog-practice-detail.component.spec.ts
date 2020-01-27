/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { BacklogPracticeDetailComponent } from 'app/entities/backlog-practice/backlog-practice-detail.component';
import { BacklogPractice } from 'app/shared/model/backlog-practice.model';

describe('Component Tests', () => {
  describe('BacklogPractice Management Detail Component', () => {
    let comp: BacklogPracticeDetailComponent;
    let fixture: ComponentFixture<BacklogPracticeDetailComponent>;
    const route = ({ data: of({ backlogPractice: new BacklogPractice(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [BacklogPracticeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BacklogPracticeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BacklogPracticeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.backlogPractice).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
