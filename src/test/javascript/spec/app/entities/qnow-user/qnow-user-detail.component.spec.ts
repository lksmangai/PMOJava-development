/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { QnowUserDetailComponent } from 'app/entities/qnow-user/qnow-user-detail.component';
import { QnowUser } from 'app/shared/model/qnow-user.model';

describe('Component Tests', () => {
  describe('QnowUser Management Detail Component', () => {
    let comp: QnowUserDetailComponent;
    let fixture: ComponentFixture<QnowUserDetailComponent>;
    const route = ({ data: of({ qnowUser: new QnowUser(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [QnowUserDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(QnowUserDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(QnowUserDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.qnowUser).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
