import { TestBed } from '@angular/core/testing';

import { EnergyProcessingTimeService } from './energy-processing-time.service';

describe('EnergyProcessingTimeService', () => {
  let service: EnergyProcessingTimeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EnergyProcessingTimeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
