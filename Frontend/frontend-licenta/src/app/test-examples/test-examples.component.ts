import { Component } from '@angular/core';
import { Chart } from 'chart.js/auto';
import { MatIconModule } from '@angular/material/icon';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AlgEvolutivService } from '../Services/alg-evolutiv.service';
import { Observable, forkJoin } from 'rxjs';

interface AlgorithmResult {
  makespan: number;
  energy: number;
  fitness: number;
}

@Component({
  selector: 'app-test-examples',
  templateUrl: './test-examples.component.html',
  styleUrls: ['./test-examples.component.css'],
  standalone: true,
  imports: [MatIconModule, FormsModule, CommonModule],
})
export class TestExamplesComponent {
  populationSize!: number;
  maxGenerations!: number;
  lambda!: number;
  result: AlgorithmResult | null = null;

  multipleResults: AlgorithmResult[] = [];
  resultsTable: { lambda: string, makespan: number, energy: number, fitness: string }[] = [];

  constructor(private algEvolutivService: AlgEvolutivService) { }

  runAlgorithm() {
    const observables: Observable<AlgorithmResult>[] = [];
    const lambdas = Array.from({ length: 11 }, (_, i) => i * 0.1);

    for (let i = 0; i < 5; i++) {
      observables.push(this.algEvolutivService.runAlgorithm(3, [], this.populationSize, this.maxGenerations, this.lambda));
    }

    // Also run for multiple lambdas
    lambdas.forEach(lambda => {
      observables.push(this.algEvolutivService.runAlgorithm(3, [], this.populationSize, this.maxGenerations, lambda));
    });

    forkJoin(observables).subscribe(results => {
      // Separate results for single lambda and multiple lambdas
      this.multipleResults = results.slice(0, 5); // First 5 are for single lambda
      const multipleLambdaResults = results.slice(5); // Rest are for multiple lambdas

      // Display result for single lambda (last one of the first 5)
      this.result = this.multipleResults[this.multipleResults.length - 1];

      // Update results table for multiple lambdas
      this.resultsTable = multipleLambdaResults.map((result, index) => ({
        lambda: lambdas[index].toFixed(1),
        makespan: result.makespan,
        energy: result.energy,
        fitness: result.fitness.toFixed(4)
      }));

      // Call method to update or create chart
      this.createLineChart();
    }, error => {
      console.error('Error fetching results:', error);
    });
  }

  lineChart: Chart | null = null;

  createLineChart(): void {
    const labels = ['Run 1', 'Run 2', 'Run 3', 'Run 4', 'Run 5'];
    const makespanData = this.multipleResults.map(result => result.makespan);
    const energyData = this.multipleResults.map(result => result.energy);
    const fitnessData = this.multipleResults.map(result => result.fitness);

    const ctx = document.getElementById('lineChart') as HTMLCanvasElement;
    if (this.lineChart) {
      this.lineChart.destroy();
    }
    this.lineChart = new Chart(ctx, {
      type: 'line',
      data: {
        labels: labels,
        datasets: [
          {
            label: 'Makespan',
            data: makespanData,
            fill: false,
            borderColor: 'rgb(75, 192, 192)',
            tension: 0.1
          },
          {
            label: 'Energy',
            data: energyData,
            fill: false,
            borderColor: 'rgb(255, 99, 132)',
            tension: 0.1
          },
          {
            label: 'Fitness',
            data: fitnessData,
            fill: false,
            borderColor: 'rgb(54, 162, 235)',
            tension: 0.1
          }
        ]
      }
    });
  }
}
