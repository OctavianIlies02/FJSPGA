import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { FileService } from '../Services/file.service';
import { WritesService } from '../Services/writes.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-generate-test',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './generate-test.component.html',
  styleUrl: './generate-test.component.css'
})
export class GenerateTestComponent {
  fileToUpload: File | null = null;
  nbJobs: number | undefined;
  nbMchs: number | undefined;
  ops: string | undefined;
  modes: string | undefined;
  message: string | null = null;

  constructor(private fileService: FileService, private writeService: WritesService) { }

  onFileSelected(event: any) {
    this.fileToUpload = event.target.files.item(0);
  }

  onFileSubmit() {
    if (this.fileToUpload) {
      this.fileService.uploadFile(this.fileToUpload).subscribe(
        response => {
          console.log('File uploaded successfully', response);
          this.message = 'File uploaded successfully!';
          setTimeout(() => this.message = null, 3000); // Clears message after 5 seconds
        },
        error => {
          console.error('Error uploading file', error);
          this.message = 'Error uploading file.';
        }
      );
    } else {
      console.error('No file selected');
      this.message = 'No file selected';
          setTimeout(() => this.message = null, 3000); // Clears message after 5 seconds
    }
  }

  // parses a string representation of operations into a three-dimensional array
 //where each sub-array represents job operations with task and machine IDs.
  parseOps(opsStr: string): number[][][] {
    const opsArray = opsStr
      .replace(/^\[\[/, '')
      .replace(/\]\]$/, '')
      .replace(/\]\]$/, '')
      .split(/\],\[/);
  
    return opsArray.map(jobOpsStr => {
      const jobOps = jobOpsStr.split(/>,</).map(opStr => {
        const cleanStr = opStr.replace(/[<>]/g, '');
        const [taskId, machineId] = cleanStr.split(',').map(Number);
        return [taskId, machineId];
      });
      return jobOps;
    });
  }
  
  //Parses a string representation of task modes into a two-dimensional array,
  //where each sub-array contains task ID, energy, and processing time.
  parseModes(modesStr: string): number[][] {
    const cleanStr = modesStr.replace(/[{}\s]/g, '');
    console.log('After removing braces and spaces: ', cleanStr);
    
    const modesArray = cleanStr.split(/>,</).map(modeStr => {
      const cleanModeStr = modeStr.replace(/[<>]/g, '');
      console.log('Clean mode string: ', cleanModeStr);
      const [taskId, energy, processingTime] = cleanModeStr.split(',').map(Number);
      return [taskId, energy, processingTime];
    });
    
    console.log('Parsed modes array: ', modesArray);
    return modesArray;
  }
  
  //Handles the form submission, parses the operations and modes strings,
  //and sends the data to a service for processing and storage.
  onSubmit() {
    if (this.nbJobs && this.nbMchs && this.ops && this.modes) {
      try {
        const opsList = this.parseOps(this.ops);
        const modesList = this.parseModes(this.modes);
  
        console.log('Parsed ops:', JSON.stringify(opsList));
        console.log('Parsed modes:', JSON.stringify(modesList));
  
        this.writeService.generateData(this.nbJobs, this.nbMchs, opsList, modesList).subscribe(
          response => {
            console.log('Success', response);
            this.message = 'Success';
            setTimeout(() => this.message = null, 3000); // Clears message after 5 seconds
          },
          error => {
            console.error('Error in data processing', error);
            this.message = 'Data processed successfully!';
            setTimeout(() => this.message = null, 3000); // Clears message after 5 seconds
          }
        );
      } catch (e) {
        console.error('Data was not sent', e);
        this.message = 'Error parsing data.';
      }
    } else {
      console.error('Missing variables');
      this.message = 'Missing variables';
          setTimeout(() => this.message = null, 3000); // Clears message after 5 seconds
    }
  }

  
  //Sends a request to delete all data through the service, handling success and error responses.
  onDeleteAllData() {
    this.writeService.deleteAllData().subscribe(
      response => {
        console.log('All data deleted successfully', response);
        this.message = 'Deleted all data';
          setTimeout(() => this.message = null, 3000); // Clears message after 5 seconds
      },
      error => {
        console.error('Error deleting all data', error);
      }
    );
  }
  

}

