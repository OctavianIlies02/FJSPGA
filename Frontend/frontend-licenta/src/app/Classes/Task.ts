import { EnergyProcessingTime } from "./EnergyProcessingTime";
import { Job } from "./Job";
import { Machine } from "./Machine";

export interface Task {
    id: number,
    job: Job,
    machine: Machine,
    finishTime: number,
    energyProcessingTimeList: EnergyProcessingTime[]
}
