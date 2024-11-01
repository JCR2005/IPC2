import { TestBed } from '@angular/core/testing';

import { VisualizacionRevistaService } from './visualizacion-revista.service';

describe('VisualizacionRevistaService', () => {
  let service: VisualizacionRevistaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VisualizacionRevistaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
