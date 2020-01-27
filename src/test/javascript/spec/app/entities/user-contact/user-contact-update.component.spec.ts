/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { UserContactUpdateComponent } from 'app/entities/user-contact/user-contact-update.component';
import { UserContactService } from 'app/entities/user-contact/user-contact.service';
import { UserContact } from 'app/shared/model/user-contact.model';

describe('Component Tests', () => {
  describe('UserContact Management Update Component', () => {
    let comp: UserContactUpdateComponent;
    let fixture: ComponentFixture<UserContactUpdateComponent>;
    let service: UserContactService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [UserContactUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(UserContactUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserContactUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserContactService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserContact(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserContact();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
