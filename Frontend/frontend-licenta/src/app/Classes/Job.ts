import { Task } from "./Task";


export interface Job {
    id: number,
    arrivalTime: number,
    task: Task
}
