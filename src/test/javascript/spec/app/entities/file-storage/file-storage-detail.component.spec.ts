/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { FileStorageDetailComponent } from 'app/entities/file-storage/file-storage-detail.component';
import { FileStorage } from 'app/shared/model/file-storage.model';

describe('Component Tests', () => {
  describe('FileStorage Management Detail Component', () => {
    let comp: FileStorageDetailComponent;
    let fixture: ComponentFixture<FileStorageDetailComponent>;
    const route = ({ data: of({ fileStorage: new FileStorage(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [FileStorageDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FileStorageDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FileStorageDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.fileStorage).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
