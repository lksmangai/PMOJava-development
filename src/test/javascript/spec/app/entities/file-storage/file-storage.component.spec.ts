/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Qnow1TestModule } from '../../../test.module';
import { FileStorageComponent } from 'app/entities/file-storage/file-storage.component';
import { FileStorageService } from 'app/entities/file-storage/file-storage.service';
import { FileStorage } from 'app/shared/model/file-storage.model';

describe('Component Tests', () => {
  describe('FileStorage Management Component', () => {
    let comp: FileStorageComponent;
    let fixture: ComponentFixture<FileStorageComponent>;
    let service: FileStorageService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [FileStorageComponent],
        providers: []
      })
        .overrideTemplate(FileStorageComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FileStorageComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FileStorageService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new FileStorage(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.fileStorages[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
