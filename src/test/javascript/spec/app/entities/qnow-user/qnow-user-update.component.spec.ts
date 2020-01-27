/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { QnowUserUpdateComponent } from 'app/entities/qnow-user/qnow-user-update.component';
import { QnowUserService } from 'app/entities/qnow-user/qnow-user.service';
import { QnowUser } from 'app/shared/model/qnow-user.model';

describe('Component Tests', () => {
  describe('QnowUser Management Update Component', () => {
    let comp: QnowUserUpdateComponent;
    let fixture: ComponentFixture<QnowUserUpdateComponent>;
    let service: QnowUserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [QnowUserUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(QnowUserUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(QnowUserUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(QnowUserService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new QnowUser(123);
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
        const entity = new QnowUser();
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
