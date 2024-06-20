import { Component } from '@angular/core';
import { AlgEvolutivService } from '../Services/alg-evolutiv.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-alg-evolutiv',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './alg-evolutiv.component.html',
  styleUrl: './alg-evolutiv.component.css'
})
export class AlgEvolutivComponent {

  populationSize!: number;
  maxGenerations!: number;
  lambda!: number;
  result: any;

  constructor(private algEvolutivService: AlgEvolutivService) { }

  runAlgorithm() {
    this.algEvolutivService.runAlgorithm(3, [], this.populationSize, this.maxGenerations, this.lambda)
      .subscribe(data => {
        this.result = data;
      });
  }
}
