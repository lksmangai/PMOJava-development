/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { FileStorageUpdateComponent } from 'app/entities/file-storage/file-storage-update.component';
import { FileStorageService } from 'app/entities/file-storage/file-storage.service';
import { FileStorage } from 'app/shared/model/file-storage.model';

describe('Component Tests', () => {
  describe('FileStorage Management Update Component', () => {
    let comp: FileStorageUpdateComponent;
    let fixture: ComponentFixture<FileStorageUpdateComponent>;
    let service: FileStorageService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [FileStorageUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FileStorageUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FileStorageUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FileStorageService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FileStorage(123);
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
        const entity = new FileStorage();
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
