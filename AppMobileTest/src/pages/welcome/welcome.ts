import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import { LoginPage } from '../login/login';
import { ComptePage } from '../compte/compte';

@Component({
  selector: 'page-welcome',
  templateUrl: 'welcome.html',
})
export class WelcomePage {
  rootPage:any = LoginPage;

  constructor(public navCtrl: NavController, public navParams: NavParams) { }

  login(){
  	this.navCtrl.push(LoginPage);
  }

  signup(){
  this.navCtrl.push(ComptePage);
  }

}
