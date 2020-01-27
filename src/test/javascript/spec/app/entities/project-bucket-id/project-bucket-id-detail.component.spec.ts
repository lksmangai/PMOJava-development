/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectBucketIdDetailComponent } from 'app/entities/project-bucket-id/project-bucket-id-detail.component';
import { ProjectBucketId } from 'app/shared/model/project-bucket-id.model';

describe('Component Tests', () => {
  describe('ProjectBucketId Management Detail Component', () => {
    let comp: ProjectBucketIdDetailComponent;
    let fixture: ComponentFixture<ProjectBucketIdDetailComponent>;
    const route = ({ data: of({ projectBucketId: new ProjectBucketId(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectBucketIdDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProjectBucketIdDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProjectBucketIdDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.projectBucketId).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
