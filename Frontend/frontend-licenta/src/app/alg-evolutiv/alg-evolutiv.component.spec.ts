import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AlgEvolutivComponent } from './alg-evolutiv.component';

describe('AlgEvolutivComponent', () => {
  let component: AlgEvolutivComponent;
  let fixture: ComponentFixture<AlgEvolutivComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AlgEvolutivComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AlgEvolutivComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
