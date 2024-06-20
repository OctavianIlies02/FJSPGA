import { Component, OnInit } from '@angular/core';
import { Chart } from 'chart.js/auto';
import {MatIconModule} from '@angular/material/icon';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AlgEvolutivService } from '../Services/alg-evolutiv.service';


@Component({
  selector: 'app-test-examples',
  templateUrl: './test-examples.component.html',
  styleUrls: ['./test-examples.component.css'],
  standalone: true,
  imports: [MatIconModule,FormsModule, CommonModule],
})
export class TestExamplesComponent {

  populationSize!: number;
  maxGenerations!: number;
  lambda!: number;
  result: any;

  constructor(private algEvolutivService: AlgEvolutivService) { }

  runAlgorithm() {
    this.algEvolutivService.runAlgorithm(3, [], this.populationSize, this.maxGenerations, this.lambda)
      .subscribe(data => {
        this.result = data;
        this.createLineChart();
      });
  }
  
  exampleData = {
    mutationRate: 0,
    mixingRate: 0,
    populationSize: 0
  };

  
  lineChart: Chart | null = null;


  createLineChart(): void {
    const labels = ['Task1', 'Task2', 'Task3', 'Task4', 'Task5', 'Task6', 'Task7'];
    const data = {
      labels: labels,
      datasets: [{
        label: 'Data set #1',
        data: [65, 59, 80, 81, 56, 55, 40],
        fill: false,
        borderColor: 'rgb(75, 192, 192)',
        tension: 0.1
      }]
    };

    const ctx = document.getElementById('lineChart') as HTMLCanvasElement;
    this.lineChart = new Chart(ctx, {
      type: 'line',
      data: data
    });
  }






 
}



