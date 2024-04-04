import { Routes } from '@angular/router';
import { VisualizeDataComponent } from './visualize-data/visualize-data.component';
import { TestExamplesComponent } from './test-examples/test-examples.component';
import { GenerateTestComponent } from './generate-test/generate-test.component';

export const routes: Routes = [
    { path: 'visualize-data', component: VisualizeDataComponent},
    { path: 'test-examples', component: TestExamplesComponent},
    { path: 'generate-test', component: GenerateTestComponent}
];
