/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Qnow1TestModule } from '../../../test.module';
import { TagTypeComponent } from 'app/entities/tag-type/tag-type.component';
import { TagTypeService } from 'app/entities/tag-type/tag-type.service';
import { TagType } from 'app/shared/model/tag-type.model';

describe('Component Tests', () => {
  describe('TagType Management Component', () => {
    let comp: TagTypeComponent;
    let fixture: ComponentFixture<TagTypeComponent>;
    let service: TagTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [TagTypeComponent],
        providers: []
      })
        .overrideTemplate(TagTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TagTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TagTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TagType(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.tagTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
