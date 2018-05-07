import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { IonicPageModule } from 'ionic-angular';
import { LoginPage } from './login';
import { TabsPage } from '../tabs/tabs';
import { WelcomePage } from '../welcome/welcome';

@NgModule({
  declarations: [
    LoginPage,
    TabsPage,
    WelcomePage 
  ],
  imports: [
  	BrowserModule,
    IonicPageModule.forChild(LoginPage)
  ]
})
export class LoginPageModule {}
