/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { UserContactDetailComponent } from 'app/entities/user-contact/user-contact-detail.component';
import { UserContact } from 'app/shared/model/user-contact.model';

describe('Component Tests', () => {
  describe('UserContact Management Detail Component', () => {
    let comp: UserContactDetailComponent;
    let fixture: ComponentFixture<UserContactDetailComponent>;
    const route = ({ data: of({ userContact: new UserContact(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [UserContactDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(UserContactDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserContactDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userContact).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
