/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectBoardIdDetailComponent } from 'app/entities/project-board-id/project-board-id-detail.component';
import { ProjectBoardId } from 'app/shared/model/project-board-id.model';

describe('Component Tests', () => {
  describe('ProjectBoardId Management Detail Component', () => {
    let comp: ProjectBoardIdDetailComponent;
    let fixture: ComponentFixture<ProjectBoardIdDetailComponent>;
    const route = ({ data: of({ projectBoardId: new ProjectBoardId(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectBoardIdDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProjectBoardIdDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProjectBoardIdDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.projectBoardId).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
