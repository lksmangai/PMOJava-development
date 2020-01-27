/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectClassDetailComponent } from 'app/entities/project-class/project-class-detail.component';
import { ProjectClass } from 'app/shared/model/project-class.model';

describe('Component Tests', () => {
  describe('ProjectClass Management Detail Component', () => {
    let comp: ProjectClassDetailComponent;
    let fixture: ComponentFixture<ProjectClassDetailComponent>;
    const route = ({ data: of({ projectClass: new ProjectClass(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectClassDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProjectClassDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProjectClassDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.projectClass).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
