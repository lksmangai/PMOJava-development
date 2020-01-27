/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectThemeDetailComponent } from 'app/entities/project-theme/project-theme-detail.component';
import { ProjectTheme } from 'app/shared/model/project-theme.model';

describe('Component Tests', () => {
  describe('ProjectTheme Management Detail Component', () => {
    let comp: ProjectThemeDetailComponent;
    let fixture: ComponentFixture<ProjectThemeDetailComponent>;
    const route = ({ data: of({ projectTheme: new ProjectTheme(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectThemeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProjectThemeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProjectThemeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.projectTheme).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
