/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectVerticalDetailComponent } from 'app/entities/project-vertical/project-vertical-detail.component';
import { ProjectVertical } from 'app/shared/model/project-vertical.model';

describe('Component Tests', () => {
  describe('ProjectVertical Management Detail Component', () => {
    let comp: ProjectVerticalDetailComponent;
    let fixture: ComponentFixture<ProjectVerticalDetailComponent>;
    const route = ({ data: of({ projectVertical: new ProjectVertical(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectVerticalDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProjectVerticalDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProjectVerticalDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.projectVertical).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
