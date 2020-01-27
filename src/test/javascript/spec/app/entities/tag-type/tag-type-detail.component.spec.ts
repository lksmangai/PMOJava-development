/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { TagTypeDetailComponent } from 'app/entities/tag-type/tag-type-detail.component';
import { TagType } from 'app/shared/model/tag-type.model';

describe('Component Tests', () => {
  describe('TagType Management Detail Component', () => {
    let comp: TagTypeDetailComponent;
    let fixture: ComponentFixture<TagTypeDetailComponent>;
    const route = ({ data: of({ tagType: new TagType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [TagTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TagTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TagTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tagType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
