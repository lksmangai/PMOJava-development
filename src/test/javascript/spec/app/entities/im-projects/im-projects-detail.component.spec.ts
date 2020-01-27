/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { ImProjectsDetailComponent } from 'app/entities/im-projects/im-projects-detail.component';
import { ImProjects } from 'app/shared/model/im-projects.model';

describe('Component Tests', () => {
  describe('ImProjects Management Detail Component', () => {
    let comp: ImProjectsDetailComponent;
    let fixture: ComponentFixture<ImProjectsDetailComponent>;
    const route = ({ data: of({ imProjects: new ImProjects(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ImProjectsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ImProjectsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ImProjectsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.imProjects).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
