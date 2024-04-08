import { Component, OnInit } from '@angular/core';
import { Machine } from '../Classes/Machine';
import { MachineService } from '../Services/machine.service';
import { HttpClientModule, HttpErrorResponse } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { JobService } from '../Services/job.service';
import { TaskService } from '../Services/task.service';
import { Task } from '../Classes/Task';
import { Job } from '../Classes/Job';
import { EnergyProcessingTimeService } from '../Services/energy-processing-time.service';
import { EnergyProcessingTime } from '../Classes/EnergyProcessingTime';

@Component({
  selector: 'app-visualize-data',
  standalone: true,
  imports: [CommonModule, HttpClientModule],
  templateUrl: './visualize-data.component.html',
  styleUrl: './visualize-data.component.css'
})
export class VisualizeDataComponent implements OnInit{

  machines : Machine[] = [];
  jobs : Job[] = [];
  tasks : Task[] = [];
  energyProcessingTimes : EnergyProcessingTime[] = [];

  numberOfJobs: number = 0;
  numberOfMachines: number = 0;
  numberOfOperations: number = 0;

  constructor(private machineService: MachineService, private jobService: JobService, private taskService: TaskService, private energyProcessingTimeService: EnergyProcessingTimeService){}
  
  ngOnInit() {
    this.getMachines();
    this.getJobs();
    this.getTasks();
    this.getEnergyProcessingTimes();
  }

  public getMachines(): void {
    this.machineService.getMachines().subscribe(
      (response: Machine[]) => {
        this.machines = response;
        this.numberOfMachines = this.machines.length;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public getJobs(): void {
    this.jobService.getJobs().subscribe(
      (response: Job[]) => {
        this.jobs = response;
        this.numberOfJobs = this.jobs.length;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public getTasks(): void {
    this.taskService.getTasks().subscribe(
      (response: Task[]) => {
        this.tasks = response;
        this.numberOfOperations = this.tasks.length;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public getEnergyProcessingTimes(): void {
    this.energyProcessingTimeService.getEnergyProcessingTimes().subscribe(
      (response: EnergyProcessingTime[]) => {
        this.energyProcessingTimes = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }
}
