import { Component } from '@angular/core';
import {NavController, NavParams } from 'ionic-angular';

import { TabsPage } from '../pages/tabs/tabs';
@Component({
	selector : 'page-loin',
	templateUrl : 'login.html',
})

/**
 * Generated class for the LoginPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */
@Component({
  selector: 'page-login',
  templateUrl: 'login.html',
})
export class LoginPage {

  constructor(public navCtrl: NavController, public navParams: NavParams) {
  }

  /*ionViewDidLoad() {
    console.log('ionViewDidLoad TabsPage');
  }*/
  
  doLogin(){
	   this.navCtrl.setRoot('HomePage');
  }

}
