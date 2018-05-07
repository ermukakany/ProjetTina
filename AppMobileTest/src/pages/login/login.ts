import { Component,ViewChild } from '@angular/core';
import {NavController, NavParams } from 'ionic-angular';

import { TabsPage } from '../tabs/tabs';
import { WelcomePage } from '../welcome/welcome';

@Component({
	selector : 'page-login',
	templateUrl : 'login.html',
})
	
export class LoginPage {
	@ViewChild('username') uname;
	@ViewChild('password') password;
	rootPage:any = TabsPage;
  pages: Array<{title: string, component: any}>;

  constructor(public navCtrl: NavController, public navParams: NavParams) {

  	this.pages = [
    	{title : 'Mon profile',component:TabsPage}];
  }

  /*ionViewDidLoad() {
    console.log('ionViewDidLoad TabsPage');
  }*/
  
  doLogin(){
	   //this.navCtrl.setRoot('TabsPage');

	   //console.log(this.uname.value, this.password.value);

	   if(this.uname.value == "admin" && this.password.value == "123"){
	   			this.navCtrl.push(TabsPage);
	   }
  }

}
