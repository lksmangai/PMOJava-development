/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectInitiativeIdDetailComponent } from 'app/entities/project-initiative-id/project-initiative-id-detail.component';
import { ProjectInitiativeId } from 'app/shared/model/project-initiative-id.model';

describe('Component Tests', () => {
  describe('ProjectInitiativeId Management Detail Component', () => {
    let comp: ProjectInitiativeIdDetailComponent;
    let fixture: ComponentFixture<ProjectInitiativeIdDetailComponent>;
    const route = ({ data: of({ projectInitiativeId: new ProjectInitiativeId(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectInitiativeIdDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProjectInitiativeIdDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProjectInitiativeIdDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.projectInitiativeId).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
