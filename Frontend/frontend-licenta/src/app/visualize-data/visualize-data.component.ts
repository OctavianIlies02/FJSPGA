import { Component, OnInit } from '@angular/core';
import { Machine } from '../Classes/Machine';
import { MachineService } from '../Services/machine.service';
import { HttpClientModule, HttpErrorResponse } from '@angular/common/http';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-visualize-data',
  standalone: true,
  imports: [CommonModule, HttpClientModule],
  templateUrl: './visualize-data.component.html',
  styleUrl: './visualize-data.component.css'
})
export class VisualizeDataComponent implements OnInit{

  machines : Machine[] = [];

  constructor(private machineService: MachineService){}
  
  ngOnInit() {
    this.getMachines();
  }

  public getMachines(): void {
    this.machineService.getMachines().subscribe(
      (response: Machine[]) => {
        this.machines = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }
}
