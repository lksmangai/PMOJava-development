/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Qnow1TestModule } from '../../../test.module';
import { UserContactComponent } from 'app/entities/user-contact/user-contact.component';
import { UserContactService } from 'app/entities/user-contact/user-contact.service';
import { UserContact } from 'app/shared/model/user-contact.model';

describe('Component Tests', () => {
  describe('UserContact Management Component', () => {
    let comp: UserContactComponent;
    let fixture: ComponentFixture<UserContactComponent>;
    let service: UserContactService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [UserContactComponent],
        providers: []
      })
        .overrideTemplate(UserContactComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserContactComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserContactService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new UserContact(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.userContacts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
