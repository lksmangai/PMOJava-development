/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectTagDetailComponent } from 'app/entities/project-tag/project-tag-detail.component';
import { ProjectTag } from 'app/shared/model/project-tag.model';

describe('Component Tests', () => {
  describe('ProjectTag Management Detail Component', () => {
    let comp: ProjectTagDetailComponent;
    let fixture: ComponentFixture<ProjectTagDetailComponent>;
    const route = ({ data: of({ projectTag: new ProjectTag(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectTagDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProjectTagDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProjectTagDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.projectTag).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
