import { TestBed } from '@angular/core/testing';

import { ComentarService } from './comentar.service';

describe('ComentarService', () => {
  let service: ComentarService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ComentarService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
