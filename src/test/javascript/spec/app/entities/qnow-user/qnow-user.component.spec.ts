/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Qnow1TestModule } from '../../../test.module';
import { QnowUserComponent } from 'app/entities/qnow-user/qnow-user.component';
import { QnowUserService } from 'app/entities/qnow-user/qnow-user.service';
import { QnowUser } from 'app/shared/model/qnow-user.model';

describe('Component Tests', () => {
  describe('QnowUser Management Component', () => {
    let comp: QnowUserComponent;
    let fixture: ComponentFixture<QnowUserComponent>;
    let service: QnowUserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [QnowUserComponent],
        providers: []
      })
        .overrideTemplate(QnowUserComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(QnowUserComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(QnowUserService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new QnowUser(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.qnowUsers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
