/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Qnow1TestModule } from '../../../test.module';
import { GroupMembersDeleteDialogComponent } from 'app/entities/group-members/group-members-delete-dialog.component';
import { GroupMembersService } from 'app/entities/group-members/group-members.service';

describe('Component Tests', () => {
  describe('GroupMembers Management Delete Component', () => {
    let comp: GroupMembersDeleteDialogComponent;
    let fixture: ComponentFixture<GroupMembersDeleteDialogComponent>;
    let service: GroupMembersService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [GroupMembersDeleteDialogComponent]
      })
        .overrideTemplate(GroupMembersDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GroupMembersDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GroupMembersService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
