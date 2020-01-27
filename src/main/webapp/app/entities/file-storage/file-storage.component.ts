import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IFileStorage } from 'app/shared/model/file-storage.model';
import { AccountService } from 'app/core';
import { FileStorageService } from './file-storage.service';

@Component({
  selector: 'jhi-file-storage',
  templateUrl: './file-storage.component.html'
})
export class FileStorageComponent implements OnInit, OnDestroy {
  fileStorages: IFileStorage[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected fileStorageService: FileStorageService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.fileStorageService
      .query()
      .pipe(
        filter((res: HttpResponse<IFileStorage[]>) => res.ok),
        map((res: HttpResponse<IFileStorage[]>) => res.body)
      )
      .subscribe(
        (res: IFileStorage[]) => {
          this.fileStorages = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInFileStorages();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IFileStorage) {
    return item.id;
  }

  registerChangeInFileStorages() {
    this.eventSubscriber = this.eventManager.subscribe('fileStorageListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
