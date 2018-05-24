import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { PopuserPage } from './popuser';

@NgModule({
  declarations: [
    PopuserPage,
  ],
  imports: [
    IonicPageModule.forChild(PopuserPage),
  ],
})
export class PopuserPageModule {}
