import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { FileService } from '../Services/file.service';
import { WritesService } from '../Services/writes.service';

@Component({
  selector: 'app-generate-test',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './generate-test.component.html',
  styleUrl: './generate-test.component.css'
})
export class GenerateTestComponent {
  fileToUpload: File | null = null;
  nbJobs: number | undefined;
  nbMchs: number | undefined;
  ops: string | undefined;
  modes: string | undefined;

  constructor(private fileService: FileService, private writeService: WritesService) { }

  onFileSelected(event: any) {
    this.fileToUpload = event.target.files.item(0);
  }

  onFileSubmit() {
    if (this.fileToUpload) {
      this.fileService.uploadFile(this.fileToUpload).subscribe(
        response => {
          console.log('File uploaded successfully', response);
        },
        error => {
          console.error('Error uploading file', error);
        }
      );
    } else {
      console.error('No file selected');
    }
  }

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
          },
          error => {
            console.error('Error in data processing', error);
          }
        );
      } catch (e) {
        console.error('Data was not sent', e);
      }
    } else {
      console.error('Missing variables');
    }
  }

  

  onDeleteAllData() {
    this.writeService.deleteAllData().subscribe(
      response => {
        console.log('All data deleted successfully', response);
      },
      error => {
        console.error('Error deleting all data', error);
      }
    );
  }
  

}

